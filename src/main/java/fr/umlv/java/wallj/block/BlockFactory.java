package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.controller.BlockControllerFactory;
import org.jbox2d.common.Vec2;

/**
 * A block factory.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class BlockFactory {

  private static Block forType(BlockType t, Vec2 pos) {
    switch (t) {
      case WALL:
        return new WallBlock(pos);
      case TRASH:
        return new TrashBlock(pos);
      case GARBAGE:
        return new GarbageBlock(pos);
      case ROBOT:
        return new RobotBlock(pos);
      case BOMB:
        return new BombBlock(pos);
      case FREE:
      default:
        return null;
    }
  }

  public static Block build(BlockType type, TileVec2 pos) {
    Block block = forType(type, pos.toVec2());
    if (block != null) block.setControllers(BlockControllerFactory.build(block));
    return block;
  }

  private BlockFactory() {
    // static class
  }

}
