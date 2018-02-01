package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

/**
 * Robot move order.
 *
 * @author Pacien TRAN-GIRARD
 */
public class MoveRobotOrder implements InputEvent {
  private final TileVec2 target;

  /**
   * @param target the target position
   */
  public MoveRobotOrder(TileVec2 target) {
    this.target = Objects.requireNonNull(target);
  }

  /**
   * @return the target tile
   */
  public TileVec2 getTarget() {
    return target;
  }
}
