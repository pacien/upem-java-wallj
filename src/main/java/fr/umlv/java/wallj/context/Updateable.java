package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.event.Event;

import java.util.stream.Stream;

/**
 * Context-updateable objects and event stream generator interface.
 *
 * @author Pacien TRAN-GIRARD
 */
@FunctionalInterface
public interface Updateable {
  /**
   * @param context an update context
   * @return a list of generated events
   */
  Stream<Event> update(Context context);
}
