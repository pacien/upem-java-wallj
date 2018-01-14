package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.block.GarbageBlock;

import java.util.List;
import java.util.Objects;

public class GarbageDisplayController extends DisplayController {

  private final GarbageBlock garbage;

  GarbageDisplayController(GarbageBlock garbage) {
    super(garbage);
    this.garbage = Objects.requireNonNull(garbage);
  }

  @Override
  public List<Event> update(Context context) {
    //TODO
    return null;
  }

}
