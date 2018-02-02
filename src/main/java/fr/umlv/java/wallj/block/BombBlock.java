package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.event.*;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import java.awt.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A bomb block.
 *
 * @author Pacien TRAN-GIRARD
 * @author Adam NAILI
 */
public class BombBlock extends JBoxBlock {
  private static final Duration TIME_INCREMENT = Duration.ofSeconds(1);
  private static final Duration MIN_TIME = Duration.ofSeconds(1);
  private static final Duration MAX_TIME = Duration.ofSeconds(9);

  private Duration timer = MIN_TIME;

  BombBlock(Vec2 pos) {
    super(BlockType.BOMB, BodyType.STATIC, SolidDef.squareShape(), pos);
  }

  private void incrementTimer() {
    timer = timer.compareTo(MAX_TIME) <= 0 ? timer.plus(TIME_INCREMENT) : MIN_TIME;
  }

  private void decrementTimer(Duration d) {
    if (timer.isNegative()) throw new IllegalStateException("This bomb has already exploded.");
    timer = timer.minus(d);
  }

  @Override
  public List<Event> update(Context context) {
    if (containsUpdateEvent(context.getEvents())) incrementTimer();
    paint(context.getGraphicsContext());
    return consume(context.getTimeDelta());
  }

  private boolean containsUpdateEvent(List<Event> events) {
    return Events.findFirst(events, BombSetupEvent.class).isPresent();
  }

  private List<Event> consume(Duration timeDelta) {
    decrementTimer(timeDelta);

    if (timer.isNegative())
      return Arrays.asList(new BombExplosionEvent(TileVec2.of(getPos())), new BlockDestroyEvent(this));
    else
      return Collections.emptyList();
  }

  private void paint(GraphicsContext graphicsContext) {
    graphicsContext.paintCircle(Color.BLACK, getPos(), TileVec2.TILE_DIM);
    Vec2 textPosition = getPos();
    textPosition.x += TileVec2.TILE_DIM / 4.0f;
    textPosition.y += 3 * TileVec2.TILE_DIM / 4.0f;
    graphicsContext.paintString(Color.RED, textPosition, Long.toString(timer.getSeconds()));
  }
}
