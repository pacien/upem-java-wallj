package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Updateable;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.*;

/**
 * A block.
 *
 * @author Pacien TRAN-GIRARD
 */
public abstract class Block implements Updateable {
  private final BlockType type;

  /**
   * @param type type of block
   */
  Block(BlockType type) {
    this.type = Objects.requireNonNull(type);
  }

  /**
   * @return the block type
   */
  public BlockType getType() {
    return type;
  }

  /**
   * @return the current tile position
   */
  public TileVec2 getTile() {
    return TileVec2.of(getPos());
  }

  /**
   * @return the current position
   */
  public abstract Vec2 getPos();

  /**
   * @param world a JBox2D world
   */
  public abstract void link(World world);

  /**
   * @param world a JBox2D world
   */
  public abstract void unlink(World world);
}
