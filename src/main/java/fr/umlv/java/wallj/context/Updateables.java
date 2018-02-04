package fr.umlv.java.wallj.context;

import fr.umlv.java.wallj.event.Event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Updateables utilities.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class Updateables {
  /**
   * @param <T>         the updateable type
   * @param context     an update context
   * @param updateables list of updateables
   * @return a stream of generated events
   */
  public static <T extends Updateable> Stream<Event> updateAll(Context context, List<T> updateables) {
    return updateables.stream()
           .flatMap(u -> u.update(context));
  }

  /**
   * @param <T>         the updateable type
   * @param context     an update context
   * @param updateables list of updateables
   * @return a stream of generated events
   */
  @SafeVarargs
  public static <T extends Updateable> Stream<Event> updateAll(Context context, T... updateables) {
    return updateAll(context, Arrays.asList(updateables));
  }

  private Updateables() {
    // static class
  }
}
