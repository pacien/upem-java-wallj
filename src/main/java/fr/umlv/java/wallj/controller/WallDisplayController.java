package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.block.WallBlock;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WallDisplayController extends BlockController {

  private final WallBlock wall;

  WallDisplayController(WallBlock wall) {
    super(wall);
    this.wall = Objects.requireNonNull(wall);
  }

  @Override
  public List<Event> update(Context context) {
    GraphicsContext graphicsContext = context.getGraphicsContext();
    graphicsContext.paintRectangle(Color.GRAY,wall.getPos(), TileVec2.TILE_DIM,TileVec2.TILE_DIM);
    return Collections.emptyList();
  }

}
