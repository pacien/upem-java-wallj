package fr.umlv.java.wallj.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Pacien TRAN-GIRARD
 */
final class PathFinderTest {

  private Path getResourcePath(String str) throws URISyntaxException {
    return Paths.get(getClass().getResource(str).toURI());
  }

  private boolean isPathConnected(List<TileVec2> path) {
    for (int i = 1; i < path.size(); ++i) {
      TileVec2 predecessor = path.get(i - 1), current = path.get(i);

      if (Math.abs(predecessor.getCol() - current.getCol()) > 1 ||
          Math.abs(predecessor.getRow() - current.getRow()) > 1) return false;

      if (Math.abs(predecessor.getCol() - current.getCol()) == 1 &&
          Math.abs(predecessor.getRow() - current.getRow()) == 1) return false;
    }

    return true;
  }

  @Test
  void testFailImpossibleFindPath() throws URISyntaxException, IOException {
    Board board = BoardParser.parse(getResourcePath("/maps/island.txt"));
    PathFinder pathFinder = new PathFinder(board);

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      pathFinder.findPath(TileVec2.of(7, 3), TileVec2.of(16, 5)); // into a wall
    });
  }

  @Test
  void testFindPath() throws URISyntaxException, IOException {
    Board board = BoardParser.parse(getResourcePath("/maps/island.txt"));
    PathFinder pathFinder = new PathFinder(board);

    TileVec2 origin = TileVec2.of(7, 3);
    TileVec2 target = TileVec2.of(33, 4);
    List<TileVec2> path = pathFinder.findPath(origin, target);

    Assertions.assertEquals(path.get(0), origin);
    Assertions.assertEquals(path.get(path.size() - 1), target);
    Assertions.assertTrue(isPathConnected(path));
    Assertions.assertTrue(path.stream().allMatch(v -> board.getBlockTypeAt(v).isTraversable()));
  }

}
