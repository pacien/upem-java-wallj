package fr.umlv.java.wallj.board;

import org.jbox2d.common.Vec2;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A typed immutable tile coordinate vector containing the coordinates of a Tile in a Board.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class TileVec2 {
  public static final int TILE_DIM = 20;
  private static final List<TileVec2> NEIGHBOR_OFFSETS = Arrays.asList(
  of(0, -1),
  of(-1, 0),
  of(0, 1),
  of(1, 0));
  private final int col, row;

  private TileVec2(int col, int row) {
    this.col = col;
    this.row = row;
  }

  /**
   * @param col the column
   * @param row the row
   * @return a corresponding tile vector
   */
  public static TileVec2 of(int col, int row) {
    return new TileVec2(col, row);
  }

  /**
   * @param v a JBox2D Vec2 vector
   * @return the coordinates of the tile containing the given point
   */
  public static TileVec2 of(Vec2 v) {
    return new TileVec2((int) (v.x / TILE_DIM), (int) (v.y / TILE_DIM));
  }

  /**
   * @return the column
   */
  public int getCol() {
    return col;
  }

  /**
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * @return the corresponding JBox2D coordinates of the top-left corner of the tile
   */
  public Vec2 toVec2() {
    return new Vec2(col * TILE_DIM, row * TILE_DIM);
  }

  public TileVec2 add(TileVec2 v) {
    return TileVec2.of(col + v.col, row + v.row);
  }

  /**
   * @return a list of the surrounding direct neighbours of this tile
   */
  public List<TileVec2> neighbors() {
    return NEIGHBOR_OFFSETS.stream().map(this::add).collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TileVec2)) return false;
    TileVec2 tileVec2 = (TileVec2) o;
    return col == tileVec2.col &&
           row == tileVec2.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(col, row);
  }
}
