package fr.umlv.java.wallj.model;

import org.jbox2d.common.Vec2;

/**
 * A robot block.
 *
 * @author Pacien TRAN-GIRARD
 */
public class RobotBlock extends Block {

  RobotBlock(Vec2 pos) {
    super(BlockType.ROBOT, pos);
  }

}
