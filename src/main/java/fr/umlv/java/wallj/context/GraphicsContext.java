package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.zen5.ScreenInfo;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * A context of the current graphic status of the application.
 *
 * @author Adam NAILI
 */
public final class GraphicsContext {
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
    graphics2D.fillOval(Math.round(position.x + (TileVec2.TILE_DIM - size) / 2) - 1, Math.round(position.y + (TileVec2.TILE_DIM - size) / 2) - 1, Math.round(size), Math.round(size));
  }

  /**
   * @param color    the color of the rectangle
   * @param position the upper left corner of the rectangle
   * @param width    width of the rectangle
   * @param height   height of the rectangle
   */
  public void paintRectangle(Color color, Vec2 position, float width, float height) {
    graphics2D.setColor(color);
    graphics2D.fillRect(Math.round(position.x), Math.round(position.y), Math.round(width), Math.round(height));
  }

  public void paintString(Color color, Vec2 position, String string){
    graphics2D.setColor(color);
    graphics2D.drawString(string,position.x, position.y);
  }
}
