package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;

import java.util.List;
import java.util.Objects;

/**
 * @author
 */
public class Stage {
  //TODO Class Stage
  private final Board currentBoard;

  public Stage(Board board) {
    currentBoard = Objects.requireNonNull(board);
  }

  /**
   * @return the current board of the game
   */
  public Board getCurrentBoard() {
    return currentBoard;
  }

  /**
   * @param context the current context
   * @return a list of new events to perform
   */
  public List<Event> update(Context context) {
    //TODO
    return null;
  }

  public boolean isCleared() {
    // TODO
    return false;
  }

}
