package fr.umlv.java.wallj.block;

import org.jbox2d.common.Vec2;

/**
 * A wall block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class WallBlock extends Block {

  WallBlock(Vec2 pos) {
    super(BlockType.WALL, pos);
  }

}
