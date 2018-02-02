package fr.umlv.java.wallj.viewer;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.event.BombSetupOrder;
import fr.umlv.java.wallj.event.ConfirmOrder;
import fr.umlv.java.wallj.event.QuitGameOrder;
import fr.umlv.java.wallj.event.MoveRobotOrder;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import org.jbox2d.common.Vec2;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * Treats the inputs from the keyboard and mouse provided by Zen 5 and creates Events meaningful for the game.
 *
 * @author Adam NAILI
 */
public final class InputHandler {
  private final ApplicationContext applicationContext;

  /**
   * @param applicationContext the Zen5 application context
   */
  public InputHandler(ApplicationContext applicationContext) {
    this.applicationContext = Objects.requireNonNull(applicationContext);
  }

  /**
   * @return the list of events converted from Zen 5 events to game events
   */
  public List<fr.umlv.java.wallj.event.Event> getEvents() {
    LinkedList<fr.umlv.java.wallj.event.Event> events = new LinkedList<>();
    fr.umlv.zen5.Event event = applicationContext.pollEvent();
    if (event != null) {
      Event.Action action = event.getAction();
      Point2D.Float location = event.getLocation();
      if (location != null) { //Mouse Handling
        if (action == Event.Action.POINTER_DOWN) {
          Vec2 mouseLocation = new Vec2(location.x, location.y);
          TileVec2 mouseTileLocation = TileVec2.of(mouseLocation);
          events.add(new MoveRobotOrder(mouseTileLocation));
        }
      }
      KeyboardKey keyboardKey = event.getKey();
      if (keyboardKey != null) { //Keyboard Handling
        if (action == Event.Action.KEY_PRESSED) {
          switch (keyboardKey) {
            case SPACE:
              events.add(new BombSetupOrder());
              break;
            case R:
              events.add(new ConfirmOrder());
              break;
            case Q:
              events.add(new QuitGameOrder());
              break;
          }
        }
      }
    }
    return events;
  }
}
