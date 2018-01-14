package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.BombBlock;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.util.Collections;
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
    GraphicsContext graphicsContext = context.getGraphicsContext();
    graphicsContext.paintCircle(Color.BLACK, bomb.getPos(), TileVec2.TILE_DIM);
    Vec2 textPosition = bomb.getPos();
    textPosition.x += TileVec2.TILE_DIM / 4.0f;
    textPosition.y += 3 * TileVec2.TILE_DIM / 4.0f;
    graphicsContext.paintString(Color.RED, textPosition, Integer.toString(bomb.getTimer()));
    return Collections.emptyList();
  }

}
