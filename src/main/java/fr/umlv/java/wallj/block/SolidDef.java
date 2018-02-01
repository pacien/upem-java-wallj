package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 * @author Pacien TRAN-GIRARD
 */
public final class SolidDef {
  public static BodyDef bodyDefOf(BodyType bodyType, Vec2 pos) {
    BodyDef def = new BodyDef();
    def.type = bodyType;
    def.position = pos;
    return def;
  }

  public static FixtureDef fixtureDefOf(Shape shape) {
    FixtureDef def = new FixtureDef();
    def.shape = shape;
    def.restitution = 1.0f;
    return def;
  }

  public static PolygonShape squareShape() {
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(TileVec2.TILE_DIM, TileVec2.TILE_DIM);
    return shape;
  }

  public static CircleShape circleShape() {
    CircleShape shape = new CircleShape();
    shape.m_radius = TileVec2.TILE_DIM / 2;
    return shape;
  }

  private SolidDef() {
    // static class
  }
}
