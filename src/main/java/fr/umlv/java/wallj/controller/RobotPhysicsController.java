package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.RobotBlock;

import java.util.List;
import java.util.Objects;

public class RobotPhysicsController extends PhysicsController {

  private final RobotBlock robot;

  RobotPhysicsController(RobotBlock robot) {
    super(robot);
    this.robot = Objects.requireNonNull(robot);
  }

  @Override
  public List<Event> update(Context context) {
    //TODO
    return null;
  }

}
