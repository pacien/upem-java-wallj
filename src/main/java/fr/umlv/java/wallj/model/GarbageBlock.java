package fr.umlv.java.wallj.model;

import org.jbox2d.common.Vec2;

/**
 * A garbage block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class GarbageBlock extends Block {

  /**
   * @param pos initial position
   */
  public GarbageBlock(Vec2 pos) {
    super(BlockType.GARBAGE, pos);
  }

}
