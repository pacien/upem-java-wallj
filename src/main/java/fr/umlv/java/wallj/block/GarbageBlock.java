package fr.umlv.java.wallj.block;

import org.jbox2d.common.Vec2;

/**
 * A garbage block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class GarbageBlock extends Block {

  GarbageBlock(Vec2 pos) {
    super(BlockType.GARBAGE, pos);
  }

}
