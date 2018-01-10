package fr.umlv.java.wallj.viewer;

import fr.umlv.zen5.ApplicationContext;

import java.awt.*;

/**
 * Link between application and Zen 5
 */
public class Viewer {

  public static void main(String[] strings) {

  }

  /**
   *
   * @param applicationContext the application context from Zen 5
   */
  public static void eventLoop(ApplicationContext applicationContext) {
    //TODO lambda exp for Application.run(Consumer<...>)
    //usage : Viewer::eventLoop
  }

  /**
   *
   * @param graphics2D the graphic2D from Zen 5
   * @param applicationContext the application context from Zen 5
   */
  public static void renderFrame(Graphics2D graphics2D,ApplicationContext applicationContext) {
    //TODO lambda exp for ApplicationContext.renderFrame(Consumer<...>)
    //usage : graphics->Viewer.renderFrame(graphics2D,applicationContext)
  }
}