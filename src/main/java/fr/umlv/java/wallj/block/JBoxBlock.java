package fr.umlv.java.wallj.block;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.ContactEdge;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A block linked to a physical body.
 *
 * @author Pacien TRAN-GIRARD
 */
public abstract class JBoxBlock extends Block {
  private final BodyDef bodyDef;
  private final FixtureDef fixtureDef;
  private Body body;

  JBoxBlock(BlockType blockType, BodyType bodyType, Shape bodyShape, Vec2 pos) {
    super(blockType);
    bodyDef = SolidDef.bodyDefOf(Objects.requireNonNull(bodyType), Objects.requireNonNull(pos));
    fixtureDef = SolidDef.fixtureDefOf(Objects.requireNonNull(bodyShape));
  }

  /**
   * @return the JBox2D body
   */
  public Body getBody() {
    return body;
  }

  /**
   * @return a stream of contact edges of this body
   */
  public Stream<ContactEdge> streamContactEdges() {
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

  @Override
  public Vec2 getPos() {
    if (body == null) throw new IllegalStateException("Uninitialised block.");
    return new Vec2(body.getPosition());
  }

  @Override
  public void link(World world) {
    if (body != null) throw new IllegalStateException("Block is already linked.");
    body = world.createBody(bodyDef);
    body.setUserData(this);
    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setUserData(body);
  }

  @Override
  public void unlink(World world) {
    if (body == null) throw new IllegalStateException("Block has not yet been linked.");
    world.destroyBody(body);
  }
}
