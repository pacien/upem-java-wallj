package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Game;
import fr.umlv.java.wallj.event.ConfirmEvent;
import fr.umlv.java.wallj.event.Event;
import fr.umlv.java.wallj.event.GameOverEvent;

import java.util.LinkedList;
import java.util.List;

public class GameController implements Controller {

  @Override
  public List<Event> update(Context context) {
    boolean isConfirmEvent = context.getEvents().stream().anyMatch(event -> event instanceof ConfirmEvent);
    boolean isGameOver = context.getEvents().stream().allMatch(event -> event instanceof GameOverEvent);
    Game currentGame = context.getGame();
    LinkedList<Event> events = new LinkedList<>();
    if (isConfirmEvent) {
      //TODO exiting
      if (!isGameOver) {
        if (currentGame.getCurrentStage().isCleared()) {
          if (currentGame.hasNextBoard()) { //continue
            currentGame.nextStage();
          } else {
            events.add(new GameOverEvent());
          }
        } else {//retry
          currentGame.retryStage();
        }
      } else {
        currentGame.setOver();
      }
    }
    return events;
  }

}
