package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.GarbageBlock;

import java.awt.*;
import java.util.Collections;
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
    GraphicsContext graphicsContext = context.getGraphicsContext();
    graphicsContext.paintCircle(new Color(102, 51, 0), garbage.getPos(),TileVec2.TILE_DIM);
    return Collections.emptyList();
  }

}
