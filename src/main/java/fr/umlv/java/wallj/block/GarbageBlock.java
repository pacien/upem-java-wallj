package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.BombExplosionEvent;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.Events;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A garbage block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class GarbageBlock extends JBoxBlock {
  private static final Color COLOR = new Color(102, 51, 0);
  private static final int STOP_RAYCAST = 0;
  private static final float EXPLOSION_RANGE = 20f * TileVec2.TILE_DIM;

  GarbageBlock(Vec2 pos) {
    super(BlockType.GARBAGE, BodyType.DYNAMIC, SolidDef.circleShape(), pos);
  }

  @Override
  public List<Event> update(Context context) {
    handleExplosionBlasts(context.getEvents(), context.getGame().getCurrentStage().getWorld());
    paint(context.getGraphicsContext());
    return Collections.emptyList();
  }

  private void handleExplosionBlasts(List<Event> events, World world) {
    Events.filter(events, BombExplosionEvent.class).forEach(explosion -> {
      Vec2 source = explosion.getSource().toVec2();
      world.raycast((fixture, point, normal, fraction) -> {
        if (isSelf(fixture)) getBody().applyForceToCenter(computeBlastForce(source));
        return STOP_RAYCAST;
      }, source, getPos());
    });
  }

  private void paint(GraphicsContext graphicsContext) {
    graphicsContext.paintCircle(COLOR, getPos(), TileVec2.TILE_DIM);
  }

  private boolean isSelf(Fixture fixture) {
    for (Fixture self = getBody().getFixtureList(); self != null; self = self.getNext())
      if (Objects.equals(fixture, self))
        return true;

    return false;
  }

  private Vec2 computeBlastForce(Vec2 source) {
    Vec2 diff = getPos().sub(source);
    float dist = diff.normalize();
    return diff.mul(EXPLOSION_RANGE / dist);
  }
}
