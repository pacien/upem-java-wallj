package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Updateables;
import fr.umlv.java.wallj.event.BlockDestroyEvent;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import java.awt.*;
import java.util.stream.Stream;

/**
 * A trash block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class TrashBlock extends JBoxBlock {
  TrashBlock(Vec2 pos) {
    super(BlockType.TRASH, BodyType.STATIC, SolidDef.squareShape(), pos);
  }

  @Override
  public Stream<Event> update(Context context) {
    return Updateables.updateAll(context,
    this::handleContacts,
    this::paint);
  }

  private Stream<Event> handleContacts(Context context) {
    return streamContactEdges()
           .map(contactEdge -> ((Block) contactEdge.other.getUserData()))
           .filter(otherBody -> otherBody.getType() == BlockType.GARBAGE)
           .map(BlockDestroyEvent::new);
  }

  private Stream<Event> paint(Context context) {
    context.getGraphicsContext().paintRectangle(Color.RED, getPos(), TileVec2.TILE_DIM, TileVec2.TILE_DIM);
    return Stream.empty();
  }
}
