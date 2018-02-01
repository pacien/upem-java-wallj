package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

/**
 * Bomb setup event.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class BombSetupEvent implements GameEvent {
  private final TileVec2 pos;

  /**
   * @param pos requested setup position
   */
  public BombSetupEvent(TileVec2 pos) {
    this.pos = Objects.requireNonNull(pos);
  }

  /**
   * @return the setup position
   */
  public TileVec2 getPos() {
    return pos;
  }
}
