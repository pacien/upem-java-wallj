package fr.umlv.java.wallj.context;

import fr.umlv.zen5.ScreenInfo;

import java.awt.Graphics2D;
import java.util.Objects;

/**
 * A context of the current graphic status of the application
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
}
