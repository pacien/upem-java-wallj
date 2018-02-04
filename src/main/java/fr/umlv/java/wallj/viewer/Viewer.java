package fr.umlv.java.wallj.viewer;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Game;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.*;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Link between application and Zen 5
 *
 * @author Adam NAILI
 * @author Pacien TRAN-GIRARD
 */
public final class Viewer {
  public static final Color BACKGROUND_COLOR = Color.WHITE;

  private static final Duration FRAME_DURATION = Duration.ofMillis(1000 / 60);
  private static int EXIT_OK = 0;

  private final ApplicationContext appContext;
  private final Game game;

  /**
   * @param boards the valid list of boards charged in the application
   */
  public Viewer(ApplicationContext appContext, List<Board> boards) {
    this.appContext = Objects.requireNonNull(appContext);
    this.game = new Game(boards);
  }

  /**
   * Launches the application
   */
  public void run() {
    List<Event> forwardEvents = new LinkedList<>();
    Duration lastExec = Duration.ZERO;

    while (!game.isOver()) {
      final Duration lastExecCopy = lastExec;
      StopWatch stopWatch = new StopWatch();
      List<Event> events = Stream.concat(forwardEvents.stream(), mapInputEvent()).collect(Collectors.toList());
      forwardEvents.clear();

      appContext.renderFrame(graphics2D -> {
        GraphicsContext graphicsContext = new GraphicsContext(graphics2D, appContext.getScreenInfo());
        clearScreen(graphicsContext);
        Context context = new Context(game, events, graphicsContext, lastExecCopy);
        game.update(context).forEach(forwardEvents::add);
      });

      sleep(FRAME_DURATION.minus(stopWatch.peek()));
      lastExec = stopWatch.peek();
    }

    appContext.exit(EXIT_OK);
  }

  private Stream<Event> mapInputEvent() {
    fr.umlv.zen5.Event inputEvent = appContext.pollEvent();
    if (inputEvent == null) return Stream.empty();

    switch (inputEvent.getAction()) {
      case POINTER_DOWN:
        Point2D.Float clickLocation = inputEvent.getLocation();
        TileVec2 tile = TileVec2.of(new Vec2(clickLocation.x, clickLocation.y));
        return Stream.of(new MoveRobotOrder(tile));

      case KEY_PRESSED:
        switch (inputEvent.getKey()) {
          case SPACE:
            return Stream.of(new BombSetupOrder());
          case R:
            return Stream.of(new ConfirmOrder());
          case Q:
            return Stream.of(new QuitGameOrder());
          case S:
            return Stream.of(new SimulationStartOrder());
          default:
            return Stream.empty();
        }

      default:
        return Stream.empty();
    }
  }

  private void clearScreen(GraphicsContext graphicsContext) {
    ScreenInfo window = graphicsContext.getScreenInfo();
    graphicsContext.paintRectangle(BACKGROUND_COLOR, new Vec2(), window.getWidth(), window.getHeight());
  }

  private void sleep(Duration duration) {
    try {
      if (!duration.isNegative()) Thread.sleep(duration.toMillis());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
