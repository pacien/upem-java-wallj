package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.event.Event;

import java.time.Duration;
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
  private final Duration timeDelta;

  /**
   * @param game            the current game
   * @param events          the list of events of the tick
   * @param graphicsContext the current graphics context
   * @param timeDelta       elapsed time since last update
   */
  public Context(Game game, List<Event> events, GraphicsContext graphicsContext, Duration timeDelta) {
    this.game = Objects.requireNonNull(game);
    this.events = Objects.requireNonNull(events);
    this.graphicsContext = Objects.requireNonNull(graphicsContext);
    this.timeDelta = Objects.requireNonNull(timeDelta);
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

  /**
   * @return the elapsed time since last update
   */
  public Duration getTimeDelta() {
    return timeDelta;
  }
}
