package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.model.Stage;

import java.util.List;
import java.util.Objects;

/**
 * A context used to store the current state of the application at one tick
 *
 * @author Adam NAILI
 */
public final class Context {
  //TODO Class Context
  private final Stage stage;
  private final List<Event> events;
  private final GraphicsContext graphicsContext;

  /**
   * @param stage           the current stage
   * @param events          the list of events of the tick
   * @param graphicsContext the current graphics context
   */
  public Context(Stage stage, List<Event> events, GraphicsContext graphicsContext) {
    this.stage = Objects.requireNonNull(stage);
    this.events = Objects.requireNonNull(events);
    this.graphicsContext = Objects.requireNonNull(graphicsContext);
  }

  /**
   * @return the stage
   */
  public Stage getStage() {
    return stage;
  }

  /**
   * @return the list of events
   */
  public List<Event> getEvents() {
    return events;
  }

  /**
   * @return the graphics context
   */
  public GraphicsContext getGraphicsContext() {
    return graphicsContext;
  }
}
