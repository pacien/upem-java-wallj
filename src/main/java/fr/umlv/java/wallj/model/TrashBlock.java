package fr.umlv.java.wallj.model;

import org.jbox2d.common.Vec2;

/**
 * A trash block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class TrashBlock extends Block {

  /**
   * @param pos initial position
   */
  public TrashBlock(Vec2 pos) {
    super(BlockType.TRASH, pos);
  }

}
