package fr.umlv.java.wallj.context;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import org.jbox2d.common.Vec2;

import java.awt.Graphics2D;
import java.util.Objects;

/**
 * Cleans the GraphicsContext
 *
 * @author Adam NAILI
 */
public final class ScreenManager {
  //TODO Class ScreenManager
  private final ApplicationContext applicationContext;
  private final Graphics2D graphics2D;

  /**
   *
   * @param applicationContext the current application context
   * @param graphics2D the current graphics2D
   */
  public ScreenManager(ApplicationContext applicationContext, Graphics2D graphics2D) {
    this.applicationContext = Objects.requireNonNull(applicationContext);
    this.graphics2D = Objects.requireNonNull(graphics2D);
  }

  /**
   *
   * @return a graphic context
   */
  public GraphicsContext clearScreen() {
    ScreenInfo screenInfo = applicationContext.getScreenInfo();
    GraphicsContext graphicsContext = new GraphicsContext(graphics2D, screenInfo);
    graphicsContext.paintRectangle(graphics2D.getBackground(), new Vec2(0, 0), screenInfo.getWidth(), screenInfo.getHeight());
    return graphicsContext;
  }
}
