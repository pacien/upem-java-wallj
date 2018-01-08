package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.model.BlockType;
import fr.umlv.java.wallj.utils.Matrix;

/**
 * An immutable BlockType matrix.
 */
public final class Board {

  /**
   * Board Builder
   */
  public static final class Builder {
    private final BlockType[][] map;

    /**
     * @param width  width in tiles
     * @param height height in tiles
     */
    public Builder(int width, int height) {
      this.map = new BlockType[width][height];
    }

    /**
     * @param pos  the tile position vector
     * @param type the BlockType to set
     * @return the Builder
     */
    public Builder setBlockTypeAt(TileVec2 pos, BlockType type) {
      map[pos.getCol()][pos.getRow()] = type;
      return this;
    }

    /**
     * @return the immutable Board
     */
    public Board build() {
      return new Board(map);
    }
  }

  private final BlockType[][] map;

  private Board(BlockType[][] map) {
    int w = Matrix.getWidth(map), h = Matrix.getHeight(map);
    this.map = new BlockType[w][h];
    for (int row = 0; row < w; ++row) System.arraycopy(map[row], 0, this.map[row], 0, h);
  }

  /**
   * @param pos the tile position vector
   * @return the element at the given position
   */
  public BlockType getBlockTypeAt(TileVec2 pos) {
    return map[pos.getCol()][pos.getRow()];
  }

  /**
   * @return the dimension of the Board
   */
  public TileVec2 getDim() {
    return TileVec2.of(Matrix.getWidth(map), Matrix.getHeight(map));
  }

}
