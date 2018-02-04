package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.PathFinder;
import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Stage;
import fr.umlv.java.wallj.context.Updateables;
import fr.umlv.java.wallj.event.*;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * A robot block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class RobotBlock extends Block {
  private static final float SPEED = 10f; // px/ms

  private final TileVec2 initialPos;
  private Vec2 pos;
  private PathFinder pathFinder;
  private Deque<TileVec2> path = new LinkedList<>();
  private int droppedBombCount = 0;
  private boolean isHiding = false;

  RobotBlock(Vec2 pos) {
    super(BlockType.ROBOT);
    this.initialPos = TileVec2.of(pos);
    this.pos = pos;
  }

  @Override
  public Vec2 getPos() {
    return new Vec2(pos);
  }

  @Override
  public void link(World world) {
    // no-op
  }

  @Override
  public void unlink(World world) {
    // no-op
  }

  @Override
  public Stream<Event> update(Context context) {
    return Updateables.updateAll(context,
    this::handleSimulationStart,
    this::updatePath,
    this::move,
    this::disappear,
    this::paint,
    this::setupBomb);
  }

  private Stream<Event> handleSimulationStart(Context context) {
    return Events.findFirst(context.getEvents(), SimulationStartEvent.class)
           .map(startEvent -> {
             isHiding = true;
             return Stream.<Event>of(new MoveRobotOrder(initialPos));
           })
           .orElse(Stream.empty());
  }

  private Stream<Event> disappear(Context context) {
    return isHiding && Objects.equals(TileVec2.of(pos), initialPos) ?
           Stream.of(new BlockDestroyEvent(this)) :
           Stream.empty();
  }

  private Stream<Event> setupBomb(Context context) {
    return Events.findFirst(context.getEvents(), BombSetupOrder.class)
           .flatMap(event -> isOnBomb(context.getGame().getCurrentStage()) ?
                             Optional.of(new BombTimerIncrEvent(getTile())) :
                             dropBomb())
           .map(Stream::of) // Optional.stream() only available in Java 9
           .orElseGet(Stream::empty);
  }

  private Optional<Event> dropBomb() {
    if (droppedBombCount >= Stage.BOMB_PLACEMENTS) return Optional.empty();
    droppedBombCount++;
    return Optional.of(new BlockCreateEvent(BlockType.BOMB, getTile()));
  }

  private Stream<Event> updatePath(Context context) {
    Events.findFirst(context.getEvents(), MoveRobotOrder.class)
    .ifPresent(event -> {
      Board board = context.getGame().getCurrentStage().getBoard();
      TileVec2 target = event.getTarget();
      if (!board.inside(target) || !board.getBlockTypeAt(target).isTraversable()) return;
      if (isHiding && !Objects.equals(target, initialPos)) return;
      if (pathFinder == null) pathFinder = new PathFinder(board);
      path = new LinkedList<>(pathFinder.findPath(TileVec2.of(pos), target));
    });
    return Stream.empty();
  }

  private Stream<Event> move(Context context) {
    if (!path.isEmpty()) {
      Vec2 dest = path.getFirst().toVec2();
      Vec2 dir = dest.sub(pos);
      float dist = dir.normalize();
      Vec2 dp = dir.mul(context.getTimeDelta().toMillis() * SPEED);
      pos = dp.length() < dist ? pos.add(dp) : path.removeFirst().toVec2();
    }
    return Stream.empty();
  }

  private Stream<Event> paint(Context context) {
    context.getGraphicsContext().paintCircle(Color.BLUE, getPos(), TileVec2.TILE_DIM / 2);
    return Stream.empty();
  }

  /**
   * @implNote TODO: profile this and consider a mapping (pos: block) for faster lookup in Stage
   */
  private boolean isOnBomb(Stage stage) {
    return stage.getBlocks().stream()
           .anyMatch(block -> Objects.equals(block.getType(), BlockType.BOMB) &&
                              Objects.equals(block.getPos(), getTile().toVec2()));
  }
}
