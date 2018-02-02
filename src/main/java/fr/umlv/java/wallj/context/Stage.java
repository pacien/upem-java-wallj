package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.block.Block;
import fr.umlv.java.wallj.block.BlockFactory;
import fr.umlv.java.wallj.block.BlockType;
import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardConverter;
import fr.umlv.java.wallj.event.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.time.Duration;
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

  /**
   * @param board the base board
   */
  public Stage(Board board) {
    this.board = Objects.requireNonNull(board);
    blocks = BoardConverter.boardToWorld(board);
    blocks.forEach(block -> block.link(world));
  }

  /**
   * @return the JBox2D world
   */
  public World getWorld() {
    return world;
  }

  /**
   * @return the base board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * @return T(this stage is cleared, i.e. does not contain any garbage block)
   * @implNote TODO: profile this and consider a garbage block counter
   */
  public boolean isCleared() {
    return blocks.stream().noneMatch(block -> block.getType() == BlockType.GARBAGE);
  }

  /**
   * @param context the current context
   * @return the list of newly generated events
   */
  @Override
  public List<Event> update(Context context) {
    updatePhysicalWorld(context.getTimeDelta());
    handleBlockDestruction(context.getEvents());
    handleBlockCreation(context.getEvents());
    return Updateable.updateAll(blocks, context);
  }

  private void updatePhysicalWorld(Duration timeDelta) {
    int dt = (int) timeDelta.toMillis();
    world.step(dt, dt * VELOCITY_TICK_PER_MS, dt * POSITION_TICK_PER_MS);
  }

  private void handleBlockDestruction(List<Event> events) {
    Events.filter(events, BlockDestroyEvent.class).forEach(event -> {
      if (blocks.remove(event.getBlock())) event.getBlock().unlink(world);
    });
  }

  private void handleBlockCreation(List<Event> events) {
    Events.filter(events, BlockCreateEvent.class).forEach(event -> {
      Block block = BlockFactory.build(event.getBlockType(), event.getPos());
      blocks.add(block);
      block.link(world);
    });
  }
}
