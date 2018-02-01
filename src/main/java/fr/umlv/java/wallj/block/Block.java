package fr.umlv.java.wallj.block;

import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Updateable;
import fr.umlv.java.wallj.controller.BlockController;
import fr.umlv.java.wallj.controller.Controller;
import fr.umlv.java.wallj.event.Event;
import org.jbox2d.common.Vec2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A block.
 *
 * @author Pacien TRAN-GIRARD
 */
public abstract class Block implements Updateable {

  private final BlockType type;
  private List<Controller> controllers;
  private Vec2 pos;

  /**
   * @param type type of block
   * @param pos  initial position
   */
  Block(BlockType type, Vec2 pos) {
    this.type = Objects.requireNonNull(type);
    this.pos = Objects.requireNonNull(pos).clone();
  }

  void setControllers(List<BlockController> l) {
    controllers = Collections.unmodifiableList(new LinkedList<>(Objects.requireNonNull(l)));
  }

  /**
   * @param pos new position to set
   */
  public void setPos(Vec2 pos) {
    this.pos = Objects.requireNonNull(pos).clone();
  }

  /**
   * @return the block type
   */
  public BlockType getType() {
    return type;
  }

  /**
   * @return the current position
   */
  public Vec2 getPos() {
    return pos.clone();
  }

  /**
   * @return the current tile position
   */
  public TileVec2 getTile() {
    return TileVec2.of(pos);
  }

  /**
   * @param ctx execution context
   * @return list of generated events
   */
  @Override
  public List<Event> update(Context ctx) {
    return controllers.stream()
           .flatMap(controller -> controller.update(ctx).stream())
           .collect(Collectors.toList());
  }

}
