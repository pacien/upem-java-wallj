package fr.umlv.java.wallj.model;

import org.jbox2d.common.Vec2;

/**
 * A wall block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class WallBlock extends Block {

  /**
   * @param pos initial position
   */
  public WallBlock(Vec2 pos) {
    super(BlockType.WALL, pos);
  }

}
