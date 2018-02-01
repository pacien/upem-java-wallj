package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.PathFinder;
import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.BombSetupEvent;
import fr.umlv.java.wallj.event.BombSetupOrder;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.MoveRobotOrder;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * A robot block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class RobotBlock extends Block {
  // TODO: define robot moving speed

  private Vec2 pos;
  private List<TileVec2> path = Collections.emptyList();
  private PathFinder pathFinder;

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
  public List<Event> update(Context context) {
    Event.findFirst(context.getEvents(), MoveRobotOrder.class)
    .ifPresent(event -> updatePath(context.getGame().getCurrentStage().getBoard(), event.getTarget()));

    if (!path.isEmpty()) move(context.getTimeDelta());
    paint(context.getGraphicsContext());
    return setupBomb(context.getEvents());
  }

  private List<Event> setupBomb(List<Event> events) {
    return Event.findFirst(events, BombSetupOrder.class)
           .map(event -> Collections.<Event>singletonList(new BombSetupEvent(TileVec2.of(pos))))
           .orElse(Collections.emptyList());
  }

  private void updatePath(Board board, TileVec2 target) {
    if (!board.getBlockTypeAt(target).isTraversable()) return;
    if (pathFinder == null) pathFinder = new PathFinder(board);
    path = pathFinder.findPath(TileVec2.of(pos), target);
  }

  private void move(Duration timeDelta) {
    // TODO: follow the current path
  }

  private void paint(GraphicsContext graphicsContext) {
    graphicsContext.paintCircle(Color.BLUE, getPos(), TileVec2.TILE_DIM / 2);
  }
}
