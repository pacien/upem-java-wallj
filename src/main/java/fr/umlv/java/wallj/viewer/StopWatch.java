package fr.umlv.java.wallj.viewer;

import java.time.Duration;

/**
 * A stopwatch that reports the elapsed time since its creation.
 *
 * @author Pacien TRAN-GIRARD
 */
public class StopWatch {
  private long startTime = System.currentTimeMillis();

  public Duration peek() {
    return Duration.ofMillis(System.currentTimeMillis() - startTime);
  }
}
