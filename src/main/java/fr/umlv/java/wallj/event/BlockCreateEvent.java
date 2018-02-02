package fr.umlv.java.wallj.event;

import fr.umlv.java.wallj.block.BlockType;
import fr.umlv.java.wallj.board.TileVec2;

import java.util.Objects;

/**
 * Block creation request.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class BlockCreateEvent implements GameEvent {
  private final BlockType blockType;
  private final TileVec2 pos;

  /**
   * @param blockType the type of the block to create
   * @param pos the position of the block to create
   */
  public BlockCreateEvent(BlockType blockType, TileVec2 pos) {
    this.blockType = Objects.requireNonNull(blockType);
    this.pos = Objects.requireNonNull(pos);
  }

  /**
   * @return the type of the block to create
   */
  public BlockType getBlockType() {
    return blockType;
  }

  /**
   * @return the position of the block to create
   */
  public TileVec2 getPos() {
    return pos;
  }
}
