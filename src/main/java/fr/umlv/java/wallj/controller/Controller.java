package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.event.Event;

import java.util.List;

/**
 * @author
 */
public interface Controller {
  List<Event> update(Context context);
}
