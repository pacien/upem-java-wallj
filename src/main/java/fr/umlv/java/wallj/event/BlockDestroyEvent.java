package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.block.Block;

import java.util.Objects;

/**
 * Block destroy request.
 *
 * @author Pacien TRAN-GIRARD
 */
public class BlockDestroyEvent implements GameEvent {
  private final Block block;

  /**
   * @param block the block to destroy
   */
  public BlockDestroyEvent(Block block) {
    this.block = Objects.requireNonNull(block);
  }

  /**
   * @return the block to destroy
   */
  public Block getBlock() {
    return block;
  }
}
