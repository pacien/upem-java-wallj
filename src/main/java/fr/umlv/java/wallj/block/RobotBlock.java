package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.PathFinder;
import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.*;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.time.Duration;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * A robot block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class RobotBlock extends Block {
  private static final float SPEED = 0.2f; // px/ms

  private Vec2 pos;
  private PathFinder pathFinder;
  private Deque<TileVec2> path = new LinkedList<>();

  RobotBlock(Vec2 pos) {
    super(BlockType.ROBOT);
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
  public List<Event> update(Context context) {
    Events.findFirst(context.getEvents(), MoveRobotOrder.class)
    .ifPresent(event -> updatePath(context.getGame().getCurrentStage().getBoard(), event.getTarget()));

    move(context.getTimeDelta());
    paint(context.getGraphicsContext());
    return setupBomb(context.getEvents());
  }

  private List<Event> setupBomb(List<Event> events) {
    return Events.findFirst(events, BombSetupOrder.class)
           .map(event -> Collections.<Event>singletonList(new BombSetupEvent(TileVec2.of(pos))))
           .orElse(Collections.emptyList());
  }

  private void updatePath(Board board, TileVec2 target) {
    if (!board.getBlockTypeAt(target).isTraversable()) return;
    if (pathFinder == null) pathFinder = new PathFinder(board);
    path = new LinkedList<>(pathFinder.findPath(TileVec2.of(pos), target));
  }

  private void move(Duration timeDelta) {
    if (path.isEmpty()) return;
    Vec2 dest = path.getFirst().toVec2();
    Vec2 dir = dest.sub(pos);
    float dist = dir.normalize();
    Vec2 dp = dir.mul(timeDelta.toMillis() * SPEED);
    pos = dp.length() < dist ? pos.add(dp) : path.removeFirst().toVec2();
  }

  private void paint(GraphicsContext graphicsContext) {
    graphicsContext.paintCircle(Color.BLUE, getPos(), TileVec2.TILE_DIM / 2);
  }
}
