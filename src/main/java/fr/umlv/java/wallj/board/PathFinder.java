package fr.umlv.java.wallj.board;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Utility to find your way in this a-maze-ing world.
 *
 * @author Pacien TRAN-GIRARD
 */
public class PathFinder {

  private static final int LEAP_COST = 1;

  private static class Node<T> {
    final T val;
    final Map<Node<T>, Integer> neighbors;

    Node(T val) {
      this.val = val;
      this.neighbors = new HashMap<>();
    }
  }

  private static class NodeSearchData<T> {
    final Node<T> predecessor;
    final double actualCost, estimatedCost;

    NodeSearchData(Node<T> predecessor, double actualCost, double estimatedCost) {
      this.predecessor = predecessor;
      this.actualCost = actualCost;
      this.estimatedCost = estimatedCost;
    }
  }

  private static double euclideanDistance(TileVec2 a, TileVec2 b) {
    return Math.sqrt(Math.pow(a.getRow() - b.getRow(), 2) +
                     Math.pow(a.getCol() - b.getCol(), 2));
  }

  private static <T> double cost(Map<Node<T>, NodeSearchData<T>> searchData, Node<T> node) {
    return Optional.ofNullable(searchData.get(node)).map(n -> n.actualCost).orElse(Double.POSITIVE_INFINITY);
  }

  private static <T> Node<T> predecessor(Map<Node<T>, NodeSearchData<T>> searchData, Node<T> node) {
    return Optional.ofNullable(searchData.get(node)).map(n -> n.predecessor).orElse(null);
  }

  private static <T> List<T> buildPath(Map<Node<T>, NodeSearchData<T>> searchData, Node<T> last) {
    LinkedList<T> path = new LinkedList<>();

    for (Node<T> node = last; node != null; node = predecessor(searchData, node))
      path.addFirst(node.val);

    return Collections.unmodifiableList(path);
  }

  private static <T> List<T> findPath(Node<T> start, T target, BiFunction<T, T, Double> heuristic) {
    Map<Node<T>, NodeSearchData<T>> searchData = new HashMap<>();
    TreeSet<Node<T>> discovered = new TreeSet<>(Comparator.comparingDouble(n -> searchData.get(n).estimatedCost));
    Set<Node<T>> visited = new HashSet<>();

    searchData.put(start, new NodeSearchData<>(null, 0, heuristic.apply(start.val, target)));
    discovered.add(start);

    Node<T> current;
    while (!discovered.isEmpty()) {
      current = discovered.pollFirst();
      if (target.equals(current.val)) return buildPath(searchData, current);

      for (Map.Entry<Node<T>, Integer> neighborEntry : current.neighbors.entrySet()) {
        if (visited.contains(neighborEntry.getKey())) continue;

        double challengeCost = cost(searchData, current) + neighborEntry.getValue();
        double currentCost = cost(searchData, neighborEntry.getKey());
        if (challengeCost < currentCost)
          searchData.put(neighborEntry.getKey(), new NodeSearchData<>(current, challengeCost,
          challengeCost + heuristic.apply(neighborEntry.getKey().val, target)));

        discovered.add(neighborEntry.getKey());
      }

      visited.add(current);
    }

    throw new IllegalArgumentException("Destination target unreachable.");
  }

  private static Map<TileVec2, Node<TileVec2>> buildGraph(Board b) {
    Map<TileVec2, Node<TileVec2>> map = b.stream()
                                        .filter(e -> e.getValue().isTraversable())
                                        .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), new Node<>(e.getKey())))
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    for (Node<TileVec2> node : map.values())
      node.val.neighbors().stream()
      .map(map::get)
      .filter(Objects::nonNull)
      .forEach(neighbor -> node.neighbors.put(neighbor, LEAP_COST));

    return map;
  }

  private final Map<TileVec2, Node<TileVec2>> graph;

  /**
   * Builds a new path finder for the supplied board.
   * A well-build (validated) board should be fully connected.
   *
   * @param board the board
   */
  public PathFinder(Board board) {
    graph = buildGraph(Objects.requireNonNull(board));
  }

  /**
   * Returns a path from a starting point to a target if it exists,
   * or throw an IllegalArgumentException if any of the given coordinates are invalid.
   * The returned path may not be the same between executions.
   *
   * @param origin the origin coordinates
   * @param target the target coordinates
   * @return a path from the origin to the target position
   * @implNote uses A* with euclidean distance heuristic
   */
  public List<TileVec2> findPath(TileVec2 origin, TileVec2 target) {
    Node<TileVec2> startNode = graph.get(origin);
    if (startNode == null) throw new IllegalArgumentException("Invalid starting point.");
    return findPath(startNode, target, PathFinder::euclideanDistance);
  }

}
