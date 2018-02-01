package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Stage;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;

import java.util.List;
import java.util.Objects;

/**
 * @author Pacien TRAN-GIRARD
 */
public class StagePhysicsController implements Controller {
  private static final int VELOCITY_TICK_PER_MS = 6;
  private static final int POSITION_TICK_PER_MS = 2;

  private final Stage stage;

  public StagePhysicsController(Stage stage) {
    this.stage = Objects.requireNonNull(stage);
  }

  @Override
  public List<Event> update(Context context) {
    int dt = (int) context.getTimeDelta().toMillis();
    stage.getWorld().step(dt, dt * VELOCITY_TICK_PER_MS, dt * POSITION_TICK_PER_MS);
    return null;
  }

}
