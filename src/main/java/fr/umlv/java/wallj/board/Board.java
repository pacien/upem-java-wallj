package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.model.BlockType;
import fr.umlv.java.wallj.utils.Matrix;

/**
 * An immutable BlockType matrix.
 *
 * @author Pacien TRAN-GIRARD
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
      map = new BlockType[height][width];
    }

    /**
     * @param pos  the tile position vector
     * @param type the BlockType to set
     * @return the Builder
     */
    public Builder setBlockTypeAt(TileVec2 pos, BlockType type) {
      map[pos.getRow()][pos.getCol()] = type;
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
    this.map = new BlockType[h][w];
    for (int row = 0; row < h; ++row) System.arraycopy(map[row], 0, this.map[row], 0, w);
  }

  /**
   * @param pos the tile position vector
   * @return the element at the given position
   */
  public BlockType getBlockTypeAt(TileVec2 pos) {
    return map[pos.getRow()][pos.getCol()];
  }

  /**
   * @return the dimension of the Board
   */
  public TileVec2 getDim() {
    return TileVec2.of(Matrix.getWidth(map), Matrix.getHeight(map));
  }

}
