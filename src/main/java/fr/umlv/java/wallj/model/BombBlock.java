package fr.umlv.java.wallj.model;

import org.jbox2d.common.Vec2;

import java.util.Objects;

/**
 * A bomb block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class BombBlock extends Block {

  private static final int INITIAL_TIMER = 1; // sec

  private int timer = INITIAL_TIMER;

  BombBlock(Vec2 pos) {
    super(BlockType.BOMB, pos);
  }

  public void setTimer(int sec) {
    timer = sec;
  }

  public int getTimer() {
    return timer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BombBlock)) return false;
    if (!super.equals(o)) return false;
    BombBlock bombBlock = (BombBlock) o;
    return timer == bombBlock.timer &&
           super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), timer);
  }

}
