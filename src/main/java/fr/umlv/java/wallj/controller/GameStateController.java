package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Game;
import fr.umlv.java.wallj.event.ConfirmOrder;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.GameOverEvent;

import java.util.LinkedList;
import java.util.List;

public class GameStateController implements Controller {

  @Override
  public List<Event> update(Context context) {
    boolean isConfirmOrder = Event.findFirst(context.getEvents(),ConfirmOrder.class).isPresent();
    boolean isGameOverEvent = Event.findFirst(context.getEvents(),GameOverEvent.class).isPresent();
    Game currentGame = context.getGame();
    LinkedList<Event> events = new LinkedList<>();
    if (isGameOverEvent) {
      currentGame.setOver();
    } else {
      if (isConfirmOrder) {
        if (currentGame.getCurrentStage().isCleared()) {
          if (currentGame.hasNextBoard()) { //continue
            currentGame.nextStage();
          } else { //no more board so game over => exiting
            currentGame.setOver();
          }
        } else {//retry
          currentGame.retryStage();
        }
      }
    }
    return events;
  }

}
