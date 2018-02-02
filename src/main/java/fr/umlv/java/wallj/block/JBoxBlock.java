package fr.umlv.java.wallj.block;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Objects;

/**
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

  @Override
  public Vec2 getPos() {
    if (body == null) throw new IllegalStateException("Uninitialised block.");
    return new Vec2(body.getPosition());
  }

  @Override
  public void link(World world) {
    if (body != null) throw new IllegalStateException("Block is already linked.");
    body = world.createBody(bodyDef);
    body.createFixture(fixtureDef);
    body.setUserData(this);
  }

  @Override
  public void unlink(World world) {
    if (body == null) throw new IllegalStateException("Block has not yet been linked.");
    world.destroyBody(body);
  }
}
