package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.block.WallBlock;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WallPhysicsController extends PhysicsController {

  private final WallBlock wall;

  WallPhysicsController(WallBlock wall) {
    super(wall);
    this.wall = Objects.requireNonNull(wall);
  }

  @Override
  public List<Event> update(Context context) {
    //TODO
    return Collections.emptyList();
  }

}
