package fr.umlv.java.wallj.viewer;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardParser;
import fr.umlv.java.wallj.board.BoardValidator;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Game;
import fr.umlv.java.wallj.context.InputHandler;
import fr.umlv.java.wallj.context.ScreenManager;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import sun.awt.image.ImageWatched;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Link between application and Zen 5
 *
 * @author Adam NAILI
 */
public final class Viewer {
  private static final Duration FRAME_DURATION = Duration.ofMillis((long) 1000.0 / 60);

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
      long timeBeforeExec = System.currentTimeMillis();
      applicationContext.renderFrame(graphics2D -> {
        events.addAll(renderFrame(graphics2D, applicationContext, events, last)); //add the new events returned by updates
      });
      long timeAfterExec = System.currentTimeMillis();
      lastExecDuration = Duration.ofMillis(timeAfterExec - timeBeforeExec);
      try {
        Duration sleepDuration = FRAME_DURATION.minus(lastExecDuration);
        if (!sleepDuration.isNegative()) Thread.sleep(sleepDuration.toMillis());
      } catch (Exception e) {
        applicationContext.exit(-1);
      }
    }
    applicationContext.exit(0);
  }

  /**
   * @param graphics2D         the graphic2D from Zen 5
   * @param applicationContext the application context from Zen 5
   */
  public List<Event> renderFrame(Graphics2D graphics2D, ApplicationContext applicationContext, List<Event> events, Duration lastExecDuration) {
    InputHandler inputHandler = new InputHandler(applicationContext);
    ScreenManager screenManager = new ScreenManager(applicationContext, graphics2D);
    events.addAll(inputHandler.getEvents());
    Context context = new Context(currentGame, events, screenManager.clearScreen(), lastExecDuration);
    List<Event> newEvents = currentGame.update(context); //return new events created from update();
    events.clear();
    return newEvents;
  }
}

