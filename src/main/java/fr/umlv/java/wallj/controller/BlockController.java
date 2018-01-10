package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.Block;

import java.util.List;
import java.util.Objects;

public abstract class BlockController implements Controller {

  private final Block block;

  BlockController(Block block) {
    this.block = Objects.requireNonNull(block);
  }

  //TODO Check UML to implement BlockController
  public abstract List<Event> update(Context context);

}
