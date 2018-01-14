package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.block.BombBlock;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BombPhysicsController extends PhysicsController {

  private final BombBlock bomb;

  BombPhysicsController(BombBlock bomb) {
    super(bomb);
    this.bomb = Objects.requireNonNull(bomb);
  }

  @Override
  public List<Event> update(Context context) {
    //TODO
    return Collections.emptyList();
  }

}
