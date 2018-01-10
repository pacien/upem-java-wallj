package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

/**
 * Event to precise that the robot is supposed to move on the tile provided
 *
 * @author Adam NAILI
 */
public class MoveRobotEvent implements InputEvent {
  //TODO Class MoveRobotEvent
  TileVec2 tileVec2;

  /**
   * @param tileVec2 the target tile
   */
  public MoveRobotEvent(TileVec2 tileVec2) {
    this.tileVec2 = Objects.requireNonNull(tileVec2);
  }
}
