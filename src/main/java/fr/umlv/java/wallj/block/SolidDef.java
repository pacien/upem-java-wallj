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
  private SolidDef() {
    // static class
  }

  /**
   * @param bodyType type of body
   * @param pos      initial position of the body
   * @return a corresponding body definition
   */
  public static BodyDef bodyDefOf(BodyType bodyType, Vec2 pos) {
    BodyDef def = new BodyDef();
    def.type = bodyType;
    def.position = pos;
    return def;
  }

  /**
   * @param shape a shape definition
   * @return a corresponding fixture definition
   */
  public static FixtureDef fixtureDefOf(Shape shape) {
    FixtureDef def = new FixtureDef();
    def.shape = shape;
    def.restitution = 1.0f;
    return def;
  }

  /**
   * @return a tile square shape definition
   */
  public static PolygonShape squareShape() {
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(TileVec2.TILE_DIM / 2, TileVec2.TILE_DIM / 2);
    return shape;
  }

  /**
   * @return a circle shape definition fitting in a tile
   */
  public static CircleShape circleShape() {
    CircleShape shape = new CircleShape();
    shape.m_radius = TileVec2.TILE_DIM / 2;
    return shape;
  }
}
