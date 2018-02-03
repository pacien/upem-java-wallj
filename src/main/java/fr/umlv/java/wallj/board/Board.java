package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.block.BlockType;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

  /**
   * @param v a tile vector
   * @return T(v is a valid position of an element within the board)
   */
  public boolean inside(TileVec2 v) {
    TileVec2 dim = getDim();
    return v.getRow() >= 0 && v.getCol() >= 0 &&
           v.getRow() < dim.getRow() && v.getCol() < dim.getCol();
  }

  /**
   * @return a stream of block types and their associated tile position vectors
   */
  public Stream<Map.Entry<TileVec2, BlockType>> stream() {
    TileVec2 dim = getDim();
    return IntStream.range(0, dim.getRow() * dim.getCol())
           .mapToObj(i -> TileVec2.of(i % dim.getCol(), i / dim.getCol()))
           .map(v -> new AbstractMap.SimpleImmutableEntry<>(v, getBlockTypeAt(v)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Board)) return false;
    Board board = (Board) o;
    return Arrays.deepEquals(map, board.map);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(map);
  }

}
