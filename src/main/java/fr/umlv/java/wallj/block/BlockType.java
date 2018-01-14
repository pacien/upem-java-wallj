package fr.umlv.java.wallj.block;

/**
 * Enumeration of the types of blocks handled in the game.
 *
 * @author Pacien TRAN-GIRARD
 */
public enum BlockType {

  FREE(false, true, true, false),
  WALL(true, false, false, false),
  TRASH(true, true, false, false),
  GARBAGE(false, true, false, true),
  ROBOT(false, false, true, false),
  BOMB(false, false, true, false);

  final boolean bounding;
  final boolean mustBeReachable;
  final boolean traversable;
  final boolean movableByExplosion;

  BlockType(boolean bounding, boolean mustBeReachable, boolean traversable, boolean movableByExplosion) {
    this.bounding = bounding;
    this.mustBeReachable = mustBeReachable;
    this.traversable = traversable;
    this.movableByExplosion = movableByExplosion;
  }

  /**
   * @return this block can bound a map
   */
  public boolean isBounding() {
    return bounding;
  }

  /**
   * @return this block must be reachable
   */
  public boolean mustBeReachable() {
    return mustBeReachable;
  }

  /**
   * @return this block is traversable
   */
  public boolean isTraversable() {
    return traversable;
  }

  /**
   * @return this block can be moved by an explosion
   */
  public boolean isMovableByExplosion() {
    return movableByExplosion;
  }

}
