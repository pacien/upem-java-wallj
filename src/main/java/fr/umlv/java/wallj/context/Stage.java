package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.block.Block;
import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardConverter;
import fr.umlv.java.wallj.event.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Pacien TRAN-GIRARD
 */
public class Stage implements Updateable {
  private static final int VELOCITY_TICK_PER_MS = 6;
  private static final int POSITION_TICK_PER_MS = 2;

  private final World world = new World(new Vec2());
  private final Board board;
  private final List<Block> blocks;

  public Stage(Board board) {
    this.board = Objects.requireNonNull(board);
    this.blocks = BoardConverter.boardToWorld(board);
    // TODO: link blocks to world
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

  private boolean isCleared() {
    // TODO
    return false;
  }

  /**
   * @param context the current context
   * @return a list of new events to perform
   */
  @Override
  public List<Event> update(Context context) {
    updatePhysicalWorld(context.getTimeDelta());
    handleBlockDestruction(context.getEvents());
    handleBlockCreation(context.getEvents());
    return generateEvents();
  }

  private void updatePhysicalWorld(Duration timeDelta) {
    int dt = (int) timeDelta.toMillis();
    world.step(dt, dt * VELOCITY_TICK_PER_MS, dt * POSITION_TICK_PER_MS);
  }

  private void handleBlockDestruction(List<Event> events) {
    Events.filter(events, BlockDestroyEvent.class).forEach(event -> {
    }); // TODO
  }

  private void handleBlockCreation(List<Event> events) {
    Events.filter(events, BlockCreateEvent.class).forEach(event -> {
    }); // TODO
  }

  private List<Event> generateEvents() {
    if (isCleared())
      return Collections.singletonList(new StageClearedEvent());
    else
      return Collections.emptyList();
  }
}
