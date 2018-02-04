package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.block.Block;
import fr.umlv.java.wallj.block.BlockFactory;
import fr.umlv.java.wallj.block.BlockType;
import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardConverter;
import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.event.BlockCreateEvent;
import fr.umlv.java.wallj.event.BlockDestroyEvent;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.Events;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author Pacien TRAN-GIRARD
 */
public class Stage implements Updateable {
  public static final int BOMB_PLACEMENTS = 3;
  private static final int VELOCITY_TICK_PER_MS = 6;
  private static final int POSITION_TICK_PER_MS = 2;

  private final World world = new World(new Vec2());
  private final List<Block> blocks = new LinkedList<>();
  private final Board board;

  /**
   * @param board the base board
   */
  public Stage(Board board) {
    this.board = Objects.requireNonNull(board);
    blocks.addAll(BoardConverter.boardToWorld(board));
    blocks.add(BlockFactory.build(BlockType.ROBOT, findAnyFreeTile(board)));
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
   * @return the list of blocks
   */
  public List<Block> getBlocks() {
    return Collections.unmodifiableList(blocks);
  }

  /**
   * @return T(this stage is cleared, i.e. does not contain any garbage block)
   * @implNote TODO: profile this and consider a garbage block counter
   */
  public boolean isCleared() {
    return blocks.stream()
           .noneMatch(block -> block.getType() == BlockType.GARBAGE);
  }

  /**
   * @return T(the physics simulation can start, i.e. the player has placed all their bombs)
   */
  public boolean isReady() {
    return blocks.stream()
           .filter(block -> block.getType() == BlockType.BOMB)
           .count() == BOMB_PLACEMENTS;
  }

  /**
   * @param context the current context
   * @return the stream of newly generated events
   */
  @Override
  public Stream<Event> update(Context context) {
    return Updateables.updateAll(context,
    this::updatePhysicalWorld,
    this::handleBlockDestruction,
    this::handleBlockCreation,
    ctx -> Updateables.updateAll(ctx, blocks));
  }

  private Stream<Event> updatePhysicalWorld(Context context) {
    int dt = (int) context.getTimeDelta().toMillis();
    world.step(dt, dt * VELOCITY_TICK_PER_MS, dt * POSITION_TICK_PER_MS);
    return Stream.empty();
  }

  private Stream<Event> handleBlockDestruction(Context context) {
    Events.filter(context.getEvents(), BlockDestroyEvent.class).forEach(event -> {
      if (blocks.remove(event.getBlock())) event.getBlock().unlink(world);
    });
    return Stream.empty();
  }

  private Stream<Event> handleBlockCreation(Context context) {
    Events.filter(context.getEvents(), BlockCreateEvent.class).forEach(event -> {
      Block block = BlockFactory.build(event.getBlockType(), event.getPos());
      blocks.add(block);
      block.link(world);
    });
    return Stream.empty();
  }

  private static TileVec2 findAnyFreeTile(Board board) {
    return board.stream()
           .filter(entry -> entry.getValue() == BlockType.FREE)
           .findAny()
           .map(Map.Entry::getKey)
           .orElseThrow(IllegalArgumentException::new);
  }
}
