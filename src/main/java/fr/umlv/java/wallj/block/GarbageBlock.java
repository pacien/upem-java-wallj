package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Updateables;
import fr.umlv.java.wallj.event.BombExplosionEvent;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.Events;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import java.awt.*;
import java.util.Objects;
import java.util.stream.Stream;

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
  public Stream<Event> update(Context context) {
    return Updateables.updateAll(context,
    this::handleExplosionBlasts,
    this::paint);
  }

  private Stream<Event> handleExplosionBlasts(Context context) {
    Events.filter(context.getEvents(), BombExplosionEvent.class).forEach(explosion -> {
      Vec2 source = explosion.getSource().toVec2();
      context.getGame().getCurrentStage().getWorld().raycast((fixture, point, normal, fraction) -> {
        if (isSelf(fixture)) getBody().applyForceToCenter(computeBlastForce(source));
        return STOP_RAYCAST;
      }, source, getPos());
    });
    return Stream.empty();
  }

  private Stream<Event> paint(Context context) {
    context.getGraphicsContext().paintCircle(COLOR, getPos(), TileVec2.TILE_DIM);
    return Stream.empty();
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
