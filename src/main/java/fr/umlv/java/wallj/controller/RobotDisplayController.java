package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.RobotBlock;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RobotDisplayController extends DisplayController {

  private final RobotBlock robot;

  RobotDisplayController(RobotBlock robot) {
    super(robot);
    this.robot = Objects.requireNonNull(robot);
  }

  @Override
  public List<Event> update(Context context) {
    GraphicsContext graphicsContext = context.getGraphicsContext();
    graphicsContext.paintCircle(Color.BLUE,robot.getPos(),TileVec2.TILE_DIM/2);
    return Collections.emptyList();
  }

}
