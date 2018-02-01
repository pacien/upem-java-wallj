package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
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
    updatePath(context.getEvents());
    if (!path.isEmpty()) move();
    paint(context.getGraphicsContext());
    return setupBomb(context.getEvents());
  }

  private List<Event> setupBomb(List<Event> events) {
    return Collections.emptyList(); // TODO: return a SetupBombEvent at current location if an order was received
  }

  private void updatePath(List<Event> events) {
    // TODO: update path if received a new target event (using the pathfinder)
  }

  private void move() {
    // TODO: follow the current path
  }

  private void paint(GraphicsContext graphicsContext) {
    graphicsContext.paintCircle(Color.BLUE, getPos(), TileVec2.TILE_DIM / 2);
  }
}
