package fr.umlv.java.wallj.event;

import java.util.List;
import java.util.Optional;

/**
 * An application event
 *
 * @author Pacien TRAN-GIRARD
 */
public interface Event {
  static <T extends Event> Optional<T> findFirst(List<Event> eventList, Class<T> eventClass) {
    return eventList.stream()
           .filter(e -> e.getClass().isInstance(eventClass))
           .findFirst()
           .map(eventClass::cast);
  }
}
