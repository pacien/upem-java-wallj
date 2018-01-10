package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A block controller factory.
 * "I am the man who arranges the blocks that descend upon me from up above..."
 *
 * @author Pacien TRAN-GIRARD
 */
public final class BlockControllerFactory {

  /**
   * @implNote no Lists.of in JDK8
   */
  private static List<BlockController> listOf(BlockController... controllers) {
    return Collections.unmodifiableList(Arrays.asList(controllers));
  }

  /**
   * Builds the controllers for the given block.
   *
   * @param b block
   * @return list of controllers
   */
  public static List<BlockController> build(Block b) {
    if (b instanceof WallBlock)
      return listOf(new WallPhysicsController((WallBlock) b), new WallDisplayController((WallBlock) b));
    if (b instanceof TrashBlock)
      return listOf(new TrashPhysicsController((TrashBlock) b), new TrashDisplayController((TrashBlock) b));
    if (b instanceof GarbageBlock)
      return listOf(new GarbagePhysicsController((GarbageBlock) b), new GarbageDisplayController((GarbageBlock) b));
    if (b instanceof RobotBlock)
      return listOf(new RobotPhysicsController((RobotBlock) b), new RobotDisplayController((RobotBlock) b));
    if (b instanceof BombBlock)
      return listOf(new BombPhysicsController((BombBlock) b), new BombDisplayController((BombBlock) b));

    return Collections.emptyList();
  }

  private BlockControllerFactory() {
    // static class
  }

}
