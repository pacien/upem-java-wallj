package fr.umlv.java.wallj.board;

import org.jbox2d.common.Vec2;

import java.util.Objects;

/**
 * A typed immutable tile coordinate vector containing the coordinates of a Tile in a Board.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class TileVec2 {

  private static final int TILE_DIM = 20;

  /**
   * @param row the row
   * @param col the column
   * @return a corresponding tile vector
   */
  public static TileVec2 of(int row, int col) {
    return new TileVec2(row, col);
  }

  /**
   * @param v a JBox2D Vec2 vector
   * @return the coordinates of the tile containing the given point
   */
  public static TileVec2 of(Vec2 v) {
    return new TileVec2((int) (v.x / TILE_DIM), (int) (v.y / TILE_DIM));
  }

  private final int row, col;

  private TileVec2(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * @return the column
   */
  public int getCol() {
    return col;
  }

  /**
   * @return the corresponding JBox2D coordinates of the top-left corner of the tile
   */
  public Vec2 toVec2() {
    return new Vec2(row * TILE_DIM, col * TILE_DIM);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TileVec2)) return false;
    TileVec2 tileVec2 = (TileVec2) o;
    return row == tileVec2.row &&
           col == tileVec2.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }

  @Override
  public String toString() {
    return "TileVec2{" +
           "row=" + row +
           ", col=" + col +
           '}';
  }

}
