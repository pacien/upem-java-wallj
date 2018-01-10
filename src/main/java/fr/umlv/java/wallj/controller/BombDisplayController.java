package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.BombBlock;

import java.util.List;
import java.util.Objects;

public class BombDisplayController extends DisplayController {

  private final BombBlock bomb;

  BombDisplayController(BombBlock bomb) {
    super(bomb);
    this.bomb = Objects.requireNonNull(bomb);
  }

  @Override
  public List<Event> update(Context context) {
    //TODO
    return null;
  }

}
