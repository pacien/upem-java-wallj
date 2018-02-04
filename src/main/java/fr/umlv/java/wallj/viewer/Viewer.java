package fr.umlv.java.wallj.viewer;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Game;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.zen5.ApplicationContext;

import java.awt.*;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Link between application and Zen 5
 *
 * @author Adam NAILI
 */
public final class Viewer {
  public static final Color BACKGROUND_COLOR = Color.WHITE;
  private static final Duration FRAME_DURATION = Duration.ofMillis(1000 / 60);

  private final Game currentGame;

  /**
   * @param boards the valid list of boards charged in the application
   */
  public Viewer(List<Board> boards) {
    this.currentGame = new Game(Objects.requireNonNull(boards));
  }

  /**
   * @param applicationContext the application context from Zen 5
   */
  public void eventLoop(ApplicationContext applicationContext) {
    List<Event> events = new LinkedList<>();
    Duration lastExecDuration = Duration.ZERO;
    while (!currentGame.isOver()) {
      Duration last = lastExecDuration;
      StopWatch stopWatch = new StopWatch();
      applicationContext.renderFrame(graphics2D -> {
        InputHandler inputHandler = new InputHandler(applicationContext);
        ScreenManager screenManager = new ScreenManager(applicationContext, graphics2D);
        events.addAll(inputHandler.getEvents());
        Context context = new Context(currentGame, events, screenManager.clearScreen(), last);
        List<Event> newEvents = currentGame.update(context).collect(Collectors.toList()); //return new events created from update();
        events.clear();
        events.addAll(newEvents); //add the new events returned by updates
      });
      try {
        Duration sleepDuration = FRAME_DURATION.minus(stopWatch.peek());
        if (!sleepDuration.isNegative()) Thread.sleep(sleepDuration.toMillis());
      } catch (Exception e) {
        applicationContext.exit(-1);
      }
      lastExecDuration = stopWatch.peek();
    }
    applicationContext.exit(0);
  }
}

