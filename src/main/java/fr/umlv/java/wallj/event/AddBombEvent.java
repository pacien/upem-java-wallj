package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

/**
 * Event to put a bomb on the game board
 *
 * @author Adam NAILI
 */
public final class AddBombEvent implements InputEvent {
  TileVec2 tileVec2;

  /**
   * @param tileVec2 a vector with coordinate relative to the tile
   */
  public AddBombEvent(TileVec2 tileVec2) {
    this.tileVec2 = Objects.requireNonNull(tileVec2);
  }
}
