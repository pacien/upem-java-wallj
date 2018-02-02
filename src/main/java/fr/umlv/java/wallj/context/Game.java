package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.controller.Controller;
import fr.umlv.java.wallj.event.ConfirmOrder;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.Events;
import fr.umlv.java.wallj.event.GameOverEvent;

import java.util.*;

/**
 * A game.
 *
 * @author Adam NAILI
 */
public final class Game implements Updateable {
  private Stage currentStage;
  private int indexBoard;
  private final List<Board> boards;
  private boolean over;

  /**
   * @param boards the list of boards charged for the game
   */
  public Game(List<Board> boards) {
    Objects.requireNonNull(boards);
    if (boards.isEmpty()) {
      throw new IllegalArgumentException("The list of boards is empty, not able to create a correct game from this.");
    }
    this.boards = Collections.unmodifiableList(boards);
    this.indexBoard = 0;
    this.currentStage = new Stage(this.boards.get(0));
    this.over = false;
  }

  /**
   * @return a boolean on the condition of having a next Board in our game.
   */
  public boolean hasNextBoard() {
    return indexBoard + 1 < boards.size();
  }

  /**
   * @return the next board
   */
  private Board nextBoard() {
    if (!hasNextBoard()) {
      throw new IllegalStateException("No more board for the game.");
    }
    indexBoard++;
    return boards.get(indexBoard);
  }

  /**
   * @return the current stage
   */
  public Stage getCurrentStage() {
    return currentStage;
  }

  public boolean isOver() {
    return over;
  }

  public void setOver() {
    over = true;
  }

  public void nextStage() {
    if (hasNextBoard()) {
      currentStage = new Stage(nextBoard());
    }
  }

  public void retryStage() {
    currentStage = new Stage(currentStage.getBoard());
  }

  /**
   * @param context the current context
   * @return a list of new events
   */
  @Override
  public List<Event> update(Context context) {
    boolean isConfirmOrder = Events.findFirst(context.getEvents(),ConfirmOrder.class).isPresent();
    boolean isGameOverEvent = Events.findFirst(context.getEvents(),GameOverEvent.class).isPresent();
    Game currentGame = context.getGame();
    LinkedList<Event> events = new LinkedList<>();
    if (isGameOverEvent) {
      currentGame.setOver();
    } else {
      if (isConfirmOrder) {
        if (currentGame.getCurrentStage().isCleared()) { // FIXME: use StageClearedEvent
          if (currentGame.hasNextBoard()) { //continue
            currentGame.nextStage();
          } else { //no more board so game over => exiting
            currentGame.setOver();
          }
        } else {//retry
          currentGame.retryStage();
        }
      }
    }

    // FIXME: update underlying stage and merge generated events
    return events;
  }


}
