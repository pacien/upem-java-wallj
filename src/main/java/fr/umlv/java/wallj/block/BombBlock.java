package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.GraphicsContext;
import fr.umlv.java.wallj.context.Updateables;
import fr.umlv.java.wallj.event.*;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import java.awt.*;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

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
  public Stream<Event> update(Context context) {
    return Updateables.updateAll(context,
    this::handleBombConfiguration,
    this::consume,
    this::paint);
  }

  private Stream<Event> handleBombConfiguration(Context context) {
    Events.findFirst(context.getEvents(), BombTimerIncrEvent.class)
    .filter(event -> Objects.equals(event.getPos(), TileVec2.of(getPos())))
    .ifPresent(event -> incrementTimer());
    return Stream.empty();
  }

  private Stream<Event> consume(Context context) {
    decrementTimer(context.getTimeDelta());
    return timer.isNegative() ?
           Stream.of(new BombExplosionEvent(TileVec2.of(getPos())), new BlockDestroyEvent(this)) :
           Stream.empty();
  }

  private Stream<Event> paint(Context context) {
    GraphicsContext graphicsContext = context.getGraphicsContext();
    graphicsContext.paintCircle(Color.BLACK, getPos(), TileVec2.TILE_DIM);
    Vec2 textPosition = getPos();
    textPosition.x += TileVec2.TILE_DIM / 4.0f;
    textPosition.y += 3 * TileVec2.TILE_DIM / 4.0f;
    graphicsContext.paintString(Color.RED, textPosition, Long.toString(timer.getSeconds()));

    return Stream.empty();
  }
}
