package fr.umlv.java.wallj.model;

import org.jbox2d.common.Vec2;

/**
 * A bomb block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class BombBlock extends Block {

  private static final int INITIAL_TIMER = 1; // sec

  private int timer = INITIAL_TIMER;

  /**
   * @param pos initial position
   */
  public BombBlock(Vec2 pos) {
    super(BlockType.BOMB, pos);
  }

  public void setTimer(int sec) {
    timer = sec;
  }

  public int getTimer() {
    return timer;
  }

}
