package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.controller.GameController;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A game
 */
public final class Game {
  //TODO
  private final GameController gameController;
  private int indexBoard;
  private final List<Board> boards;

  /**
   * @param gameController the controller of the game
   * @param boards         the list of boards charged for the game
   */
  public Game(GameController gameController, List<Board> boards) {
    this.gameController = Objects.requireNonNull(gameController);
    Objects.requireNonNull(boards);
    if (boards.isEmpty()) {
      throw new IllegalArgumentException("The list of boards is empty, not able to create a correct game from this.");
    }
    this.boards = Collections.unmodifiableList(boards);
    this.indexBoard = 0;
  }

  /**
   * @return the game controller
   */
  public GameController getGameController() {
    return gameController;
  }

  /**
   * @return the current board of the game.
   */
  public Board getCurrentBoard() {
    return boards.get(indexBoard);
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
  public Board nextBoard() {
    if (indexBoard >= boards.size()) {
      throw new NoSuchElementException("No more board for the game.");
    }
    indexBoard++;
    return boards.get(indexBoard);
  }
}
