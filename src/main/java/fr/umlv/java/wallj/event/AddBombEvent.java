package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

public final class AddBombEvent implements Event {
  TileVec2 tileVec2;

  public AddBombEvent(TileVec2 tileVec2) {
    this.tileVec2 = Objects.requireNonNull(tileVec2);
  }
}
