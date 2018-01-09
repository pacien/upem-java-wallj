package fr.umlv.java.wallj.context;

import fr.umlv.zen5.ScreenInfo;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * A context of the current graphic status of the application.
 */
public final class GraphicsContext {
  //TODO Class GraphicsContext
  private final Graphics2D graphics2D;
  private final ScreenInfo screenInfo;

  /**
   * @param graphics2D the current drawable canvas
   * @param screenInfo the informations about the screen
   */
  public GraphicsContext(Graphics2D graphics2D, ScreenInfo screenInfo) {
    this.graphics2D = Objects.requireNonNull(graphics2D);
    this.screenInfo = Objects.requireNonNull(screenInfo);
  }

  /**
   * @return the drawable canvas
   */
  public Graphics2D getGraphics2D() {
    return graphics2D;
  }

  /**
   * @return the screen informations
   */
  public ScreenInfo getScreenInfo() {
    return screenInfo;
  }

  /**
   * @param color    the color of the circle
   * @param position the upper left corner of the rectangle frame that contains the circle
   * @param size     size of the circle
   */
  public void paintCircle(Color color, Vec2 position, float size) {
    graphics2D.setColor(color);
    graphics2D.fill(new Ellipse2D.Float(position.x, position.y, size, size));
  }

  /**
   * @param color    the color of the rectangle
   * @param position the upper left corner of the rectangle
   * @param width    width of the rectangle
   * @param height   height of the rectangle
   */
  public void paintRectangle(Color color, Vec2 position, float width, float height) {
    graphics2D.setColor(color);
    graphics2D.fill(new Rectangle2D.Float(position.x, position.y, width, height));
  }
}
