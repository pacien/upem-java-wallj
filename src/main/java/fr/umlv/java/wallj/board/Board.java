package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.model.BlockType;

/**
 * A mutable BlockType matrix.
 */
public final class Board {

  private final BlockType[][] map;

  public Board(int width, int height) {
    map = new BlockType[width][height];
  }

  /**
   * @param pos the tile position vector
   * @return the element at the given position
   */
  public BlockType getBlockTypeAt(TileVec2 pos) {
    return map[pos.getCol()][pos.getRow()];
  }

  /**
   * @param pos  the tile position vector
   * @param type the BlockType to set
   * @return the overwritten element
   */
  public BlockType setBlockTypeAt(TileVec2 pos, BlockType type) {
    BlockType old = map[pos.getCol()][pos.getRow()];
    map[pos.getCol()][pos.getRow()] = type;
    return old;
  }

  /**
   * @return the dimension of the Board
   */
  public TileVec2 getDim() {
    return TileVec2.of(map.length, map.length > 0 ? map[0].length : 0);
  }

}
