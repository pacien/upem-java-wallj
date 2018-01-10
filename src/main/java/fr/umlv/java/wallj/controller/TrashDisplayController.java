package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.TrashBlock;

import java.util.List;
import java.util.Objects;

public class TrashDisplayController extends DisplayController {

  private final TrashBlock trash;

  TrashDisplayController(TrashBlock trash) {
    super(trash);
    this.trash = Objects.requireNonNull(trash);
  }

  @Override
  public List<Event> update(Context context) {
    //TODO
    return null;
  }

}
