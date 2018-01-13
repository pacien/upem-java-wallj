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
  private final Game game;
  private final List<Event> events;
  private final GraphicsContext graphicsContext;

  /**
   * @param game           the current game
   * @param events          the list of events of the tick
   * @param graphicsContext the current graphics context
   */
  public Context(Game game, List<Event> events, GraphicsContext graphicsContext) {
    this.game = Objects.requireNonNull(game);
    this.events = Objects.requireNonNull(events);
    this.graphicsContext = Objects.requireNonNull(graphicsContext);
  }

  /**
   * @return the stage
   */
  public Game getGame() {
    return game;
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
