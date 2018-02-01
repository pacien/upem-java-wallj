package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardConverter;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Updateable;
import fr.umlv.java.wallj.controller.Controller;
import fr.umlv.java.wallj.controller.StagePhysicsController;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Pacien TRAN-GIRARD
 */
public class Stage implements Updateable {
  private final List<Controller> controllers = Collections.singletonList(new StagePhysicsController(this));
  private final World world = new World(new Vec2());

  private final Board board;
  private final List<Block> blocks;

  public Stage(Board board) {
    this.board = Objects.requireNonNull(board);
    this.blocks = BoardConverter.boardToWorld(board);
  }

  public World getWorld() {
    return world;
  }

  /**
   * @return the current board of the game
   */
  public Board getBoard() {
    return board;
  }

  public boolean isCleared() {
    // TODO
    return false;
  }

  /**
   * @param context the current context
   * @return a list of new events to perform
   */
  @Override
  public List<Event> update(Context context) {
    //TODO
    return null;
  }

}
