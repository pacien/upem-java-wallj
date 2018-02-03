package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

/**
 * Bomb setup event signalling the update of a bomb at a given position.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class BombTimerIncrEvent implements GameEvent {
  private final TileVec2 pos;

  /**
   * @param pos requested setup position
   */
  public BombTimerIncrEvent(TileVec2 pos) {
    this.pos = Objects.requireNonNull(pos);
  }

  /**
   * @return the setup position
   */
  public TileVec2 getPos() {
    return pos;
  }
}
