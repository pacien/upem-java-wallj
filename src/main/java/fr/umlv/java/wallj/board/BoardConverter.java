package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam NAILI
 */
public final class BoardConverter {
  //TODO
  private BoardConverter() {
  }

  public static Board worldToBoard(List<Block> blocks) {
    int width = blocks.stream().map(Block::getTile).mapToInt(TileVec2::getRow).max().orElse(-1) + 1;
    int height = blocks.stream().map(Block::getTile).mapToInt(TileVec2::getCol).max().orElse(-1) + 1;

    Board.Builder builder = new Board.Builder(width, height);
    for (Block block : blocks) {
      builder.setBlockTypeAt(block.getTile(), block.getType());
    }
    return builder.build();
  }

  public static List<Block> boardToWorld(Board board) {
    ArrayList<Block> blocks = new ArrayList<>();
    int nbRow = board.getDim().getRow();
    int nbCol = board.getDim().getCol();
    for (int i = 0; i < nbRow; i++) {
      for (int j = 0; i < nbCol; j++) {
        Block block;
        TileVec2 location = TileVec2.of(i, j);
        block = BlockFactory.build(board.getBlockTypeAt(location), location);
        if (block != null) {
          blocks.add(block);
        }
      }
    }
    return blocks;
  }
}
