package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.event.Event;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pacien TRAN-GIRARD
 */
public interface Updateable {
  /**
   * @param context an update context
   * @return a list of generated events
   */
  List<Event> update(Context context);

  /**
   * @param updateables list of updateables
   * @param context     an update context
   * @return a list of collected generated events
   */
  static List<Event> updateAll(List<Updateable> updateables, Context context) {
    return updateables.stream()
           .flatMap(u -> u.update(context).stream())
           .collect(Collectors.toList());
  }
}
