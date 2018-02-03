package fr.umlv.java.wallj.event;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Events filtering utilities.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class Events {
  /**
   * @param eventList  list of events to filter
   * @param eventClass event class to keep
   * @param <T>        type of events to keep
   * @return a filtered stream of events
   */
  public static <T extends Event> Stream<T> filter(List<Event> eventList, Class<T> eventClass) {
    return eventList.stream()
           .filter(e -> e.getClass().isAssignableFrom(eventClass))
           .map(eventClass::cast);
  }

  /**
   * @param eventList  list of events to filter
   * @param eventClass event class to keep
   * @param <T>        type of events to keep
   * @return any matching event
   */
  public static <T extends Event> Optional<T> findFirst(List<Event> eventList, Class<T> eventClass) {
    return filter(eventList, eventClass).findFirst();
  }

  private Events() {
    // static class
  }
}
