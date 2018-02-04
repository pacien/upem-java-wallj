package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.event.ConfirmOrder;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.Events;
import fr.umlv.java.wallj.event.QuitGameOrder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
  private boolean hasNextBoard() {
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

  /**
   * @return the status of the game
   */
  public boolean isOver() {
    return over;
  }

  private void nextStage() {
    if (hasNextBoard()) {
      currentStage = new Stage(nextBoard());
    }
  }

  private void retryStage() {
    currentStage = new Stage(currentStage.getBoard());
  }

  private void goToNext() {
    if (hasNextBoard()) { //continue
      nextStage();
      return;
    }
    over = true;
  }

  private void handleEvents(Context context) {
    if (Events.findFirst(context.getEvents(), QuitGameOrder.class).isPresent()) {
      over = true;
      return;
    }
    if (Events.findFirst(context.getEvents(), ConfirmOrder.class).isPresent()) {
      if (currentStage.isCleared()) {
        goToNext();
        return;
      }
      retryStage();
    }
  }

  /**
   * @param context the current context
   * @return a list of new events
   */
  @Override
  public Stream<Event> update(Context context) {
    handleEvents(context);
    return currentStage.update(context);
  }
}
