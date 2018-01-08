package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.model.BlockType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

final class BoardParserTest {

  private Path getResourcePath(String str) throws URISyntaxException {
    return Paths.get(getClass().getResource(str).toURI());
  }

  @Test
  void testFailParseBadShape() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      BoardParser.parse(getResourcePath("/maps/nonRectangular.txt"));
    });
  }

  @Test
  void testParse() throws IOException, URISyntaxException {
    BlockType W = BlockType.wALL, F = BlockType.FREE, G = BlockType.GARBAGE, T = BlockType.TRASH;
    BlockType[][] blocks = new BlockType[][]{
    {W, W, W, W, W, W},
    {W, F, G, F, T, W},
    {W, W, W, W, W, W}};

    Board board = BoardParser.parse(getResourcePath("/maps/smallValid.txt"));

    Assertions.assertEquals(board.getDim(), TileVec2.of(blocks.length, blocks[0].length));
    for (int row = 0; row < blocks.length; ++row)
      for (int col = 0; col < blocks[0].length; ++col)
        Assertions.assertEquals(board.getBlockTypeAt(TileVec2.of(row, col)), blocks[row][col]);
  }

}
