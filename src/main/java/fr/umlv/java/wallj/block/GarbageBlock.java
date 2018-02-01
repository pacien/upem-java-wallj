package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * A garbage block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class GarbageBlock extends JBoxBlock {
  private static final Color COLOR = new Color(102, 51, 0);

  GarbageBlock(Vec2 pos) {
    super(BlockType.GARBAGE, BodyType.DYNAMIC, SolidDef.circleShape(), pos);
  }

  @Override
  public List<Event> update(Context context) {
    paint(context.getGraphicsContext());
    return Collections.emptyList();
  }

  private void paint(GraphicsContext graphicsContext) {
    graphicsContext.paintCircle(COLOR, getPos(), TileVec2.TILE_DIM);
  }
}
