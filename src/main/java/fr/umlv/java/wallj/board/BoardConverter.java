package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Operations of conversion on Board and List of blocks
 * @author Adam NAILI
 */
public final class BoardConverter {
  private BoardConverter() {
  }

  /**
   * @param blocks the list of blocks to convert in a board
   * @return the converted board
   */
  public static Board worldToBoard(List<Block> blocks) {
    int width = blocks.stream().map(Block::getTile).mapToInt(TileVec2::getCol).max().orElse(-1) + 1;
    int height = blocks.stream().map(Block::getTile).mapToInt(TileVec2::getRow).max().orElse(-1) + 1;

    Board.Builder builder = new Board.Builder(width, height);
    for (Block block : blocks) {
      builder.setBlockTypeAt(block.getTile(), block.getType());
    }
    return builder.build();
  }

  /**
   * @param board the board to convert into a list of blocks
   * @return the list of blocks converted
   */
  public static List<Block> boardToWorld(Board board) {
    ArrayList<Block> blocks = new ArrayList<>();
    int nbRow = board.getDim().getRow();
    int nbCol = board.getDim().getCol();
    for (int i = 0; i < nbRow; i++) {
      for (int j = 0; j < nbCol; j++) {
        Block block;
        TileVec2 location = TileVec2.of(j, i);
        block = BlockFactory.build(board.getBlockTypeAt(location), location);
        if (block != null) {
          blocks.add(block);
        }
      }
    }
    return blocks;
  }
}
