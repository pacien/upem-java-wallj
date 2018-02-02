package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

/**
 * Signals the explosion of a bomb.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class BombExplosionEvent {
  private final TileVec2 source;

  public BombExplosionEvent(TileVec2 source) {
    this.source = Objects.requireNonNull(source);
  }

  public TileVec2 getSource() {
    return source;
  }
}
