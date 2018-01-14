package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.TrashBlock;

import java.awt.*;
import java.util.Collections;
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
    GraphicsContext graphicsContext = context.getGraphicsContext();
    graphicsContext.paintRectangle(Color.RED,trash.getPos(), TileVec2.TILE_DIM,TileVec2.TILE_DIM);
    return Collections.emptyList();
  }

}
