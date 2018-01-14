package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.model.BlockType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Board validity checker.
 *
 * @author Pacien TRAN-GIRARD
 */
public class BoardValidator {

  /**
   * A validation exception, witness of validation error(s).
   */
  public static class ValidationException extends Exception {
    ValidationException(String msg) {
      super(msg);
    }

    ValidationException() {
      super();
    }
  }

  /**
   * Constraint predicates.
   */
  public static final class Constraint {

    public static final int NB_DROPPABLE_BOMBS = 3;

    private static boolean inBoard(TileVec2 dim, TileVec2 v) {
      return v.getRow() >= 0 && v.getRow() < dim.getRow() &&
             v.getCol() >= 0 && v.getRow() < dim.getCol();
    }

    /**
     * Checks that the supplied board is well-bounded.
     */
    public static boolean isBounded(Board b) {
      TileVec2 dim = b.getDim();

      boolean hBounded = IntStream.range(0, dim.getCol())
                         .allMatch(col -> b.getBlockTypeAt(TileVec2.of(col, 0)).isBounding() &&
                                          b.getBlockTypeAt(TileVec2.of(col, dim.getRow() - 1)).isBounding());

      boolean vBounded = IntStream.range(0, dim.getRow())
                         .allMatch(row -> b.getBlockTypeAt(TileVec2.of(0, row)).isBounding() &&
                                          b.getBlockTypeAt(TileVec2.of(dim.getCol() - 1, row)).isBounding());

      return hBounded && vBounded;
    }

    /**
     * Checks that the supplied board is hollow, i.e. has a unique and simple interior.
     */
    public static boolean isHollow(Board b) {
      TileVec2 dim = b.getDim();
      Optional<TileVec2> start = b.stream().filter(e -> e.getValue().isTraversable()).findAny().map(Map.Entry::getKey);
      if (!start.isPresent()) return false;

      Set<TileVec2> reachable = new HashSet<>();
      Queue<TileVec2> toVisit = new ArrayDeque<>();
      reachable.add(start.get());
      toVisit.add(start.get());

      TileVec2 tile;
      while ((tile = toVisit.poll()) != null)
        tile.neighbors().stream()
        .filter(neighbor -> inBoard(dim, neighbor))
        .filter(neighbor -> b.getBlockTypeAt(neighbor) != null && b.getBlockTypeAt(neighbor).isTraversable())
        .filter(neighbor -> !reachable.contains(neighbor))
        .forEach(neighbor -> {
          reachable.add(neighbor);
          toVisit.add(neighbor);
        });

      return reachable.size() == b.stream().filter(e -> e.getValue().isTraversable()).count();
    }

    /**
     * Checks that every block marked as reachable has at least one traversable neighbor.
     */
    public static boolean hasActualReachableBlocks(Board b) {
      TileVec2 dim = b.getDim();
      return b.stream()
             .filter(blockEntry -> blockEntry.getValue().mustBeReachable())
             .allMatch(blockEntry -> blockEntry.getKey().neighbors().stream()
                                     .filter(neighbor -> inBoard(dim, neighbor))
                                     .anyMatch(neighbor -> b.getBlockTypeAt(neighbor).isTraversable()));
    }

    /**
     * Checks that the supplied board has all the mandatory blocks.
     */
    public static boolean hasMandatoryBlocks(Board b) {
      return b.stream().anyMatch(block -> block.getValue() == BlockType.TRASH) &&
             b.stream().anyMatch(block -> block.getValue() == BlockType.GARBAGE) &&
             b.stream().filter(block -> block.getValue() == BlockType.FREE).count() >= NB_DROPPABLE_BOMBS;
    }

    private Constraint() {
      // static class
    }

  }

  private final Board board;
  private final ValidationException errors = new ValidationException();

  /**
   * @param board the board to validate
   */
  public BoardValidator(Board board) {
    this.board = board;
  }

  /**
   * Tests the board against a given validator, using the supplied error message if the validation fails.
   *
   * @param validator a validity test
   * @param msg       a failure message
   * @return the board validator
   */
  public BoardValidator validate(Predicate<Board> validator, String msg) {
    if (!validator.test(board)) errors.addSuppressed(new ValidationException(msg));
    return this;
  }

  /**
   * @return the validated board
   * @throws ValidationException in case of failure
   */
  public Board get() throws ValidationException {
    if (errors.getSuppressed().length > 0)
      throw errors;
    else
      return board;
  }

}
