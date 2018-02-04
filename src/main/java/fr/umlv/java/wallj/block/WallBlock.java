package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Updateables;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import java.awt.*;
import java.util.stream.Stream;

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
  public Stream<Event> update(Context context) {
    return Updateables.updateAll(context,
    this::paint);
  }

  private Stream<Event> paint(Context context) {
    context.getGraphicsContext().paintRectangle(Color.BLACK, getPos(), TileVec2.TILE_DIM, TileVec2.TILE_DIM);
    return Stream.empty();
  }
}
