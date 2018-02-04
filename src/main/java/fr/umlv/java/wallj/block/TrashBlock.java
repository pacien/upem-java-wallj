package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Updateables;
import fr.umlv.java.wallj.event.BlockDestroyEvent;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.ContactEdge;

import java.awt.*;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

  private Stream<ContactEdge> streamContactEdges() {
    // If only we had (a working) Java 9...
    // return Stream.iterate(getBody().getContactList(), c -> c.next)
    //        .takeWhile(Objects::nonNull);

    return StreamSupport.stream(new Spliterator<ContactEdge>() {
      private ContactEdge contactEdge = getBody().getContactList();

      @Override
      public boolean tryAdvance(Consumer<? super ContactEdge> consumer) {
        if (contactEdge == null) return false;

        consumer.accept(contactEdge);
        contactEdge = contactEdge.next;
        return true;
      }

      @Override
      public Spliterator<ContactEdge> trySplit() {
        return null;
      }

      @Override
      public long estimateSize() {
        return Long.MAX_VALUE;
      }

      @Override
      public int characteristics() {
        return NONNULL;
      }
    }, false);
  }
}
