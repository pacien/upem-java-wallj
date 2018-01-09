package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

public class MoveRobotEvent implements InputEvent {
  //TODO Class MoveRobotEvent
  TileVec2 tileVec2;

  public MoveRobotEvent(TileVec2 tileVec2) {
    this.tileVec2 = Objects.requireNonNull(tileVec2);
  }
}
