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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Link between application and Zen 5
 */
public final class Viewer {

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
    while (!currentGame.isOver()) {
      long timeBeforeExec = System.currentTimeMillis();
      applicationContext.renderFrame(graphics2D -> {
        events.addAll(renderFrame(graphics2D, applicationContext, events)); //add the new events returned by updates
      });
      long timeAfterExec = System.currentTimeMillis();
      long delay = timeAfterExec - timeBeforeExec;
      try {
        Thread.sleep(delay > 0 ? delay : 0);
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
  public List<Event> renderFrame(Graphics2D graphics2D, ApplicationContext applicationContext, List<Event> events) {
    InputHandler inputHandler = new InputHandler(applicationContext);
    ScreenManager screenManager = new ScreenManager(applicationContext, graphics2D);
    events.addAll(inputHandler.getEvents());
    Context context = new Context(currentGame, events, screenManager.clearScreen());
    List<Event> newEvents = currentGame.update(context); //return new events created from update();
    events.clear();
    return newEvents;
  }
}

