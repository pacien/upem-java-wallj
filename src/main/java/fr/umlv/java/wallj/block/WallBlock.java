package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * A wall block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class WallBlock extends JBoxBlock {
  WallBlock(Vec2 pos) {
    super(BlockType.WALL, BodyType.STATIC, SolidDef.squareShape(), pos);
  }

  @Override
  public List<Event> update(Context context) {
    paint(context.getGraphicsContext());
    return Collections.emptyList();
  }

  private void paint(GraphicsContext graphicsContext) {
    graphicsContext.paintRectangle(Color.BLACK, getPos(), TileVec2.TILE_DIM, TileVec2.TILE_DIM);
  }
}
