package fr.umlv.java.wallj.block;

import org.jbox2d.common.Vec2;

/**
 * A trash block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class TrashBlock extends Block {

  TrashBlock(Vec2 pos) {
    super(BlockType.TRASH, pos);
  }

}
