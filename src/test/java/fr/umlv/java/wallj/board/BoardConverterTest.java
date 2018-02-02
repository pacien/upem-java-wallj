package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.block.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adam NAILI
 */
final class BoardConverterTest {

  @Test
  void testWorldToBoard() {
    List<Block> blocks = new LinkedList<>();
    Board.Builder builder = new Board.Builder(5, 1);
    TileVec2 t0 = TileVec2.of(0, 0);
    TileVec2 t1 = TileVec2.of(1, 0);
    TileVec2 t2 = TileVec2.of(2, 0);
    TileVec2 t3 = TileVec2.of(3, 0);
    TileVec2 t4 = TileVec2.of(4, 0);

    builder.setBlockTypeAt(t0, BlockType.WALL);
    builder.setBlockTypeAt(t1, BlockType.BOMB);
    builder.setBlockTypeAt(t2, BlockType.GARBAGE);
    builder.setBlockTypeAt(t3, BlockType.ROBOT);
    builder.setBlockTypeAt(t4, BlockType.TRASH);
    Board certifiedBoard = builder.build();

    blocks.add(BlockFactory.build(BlockType.WALL, t0));
    blocks.add(BlockFactory.build(BlockType.BOMB, t1));
    blocks.add(BlockFactory.build(BlockType.GARBAGE, t2));
    blocks.add(BlockFactory.build(BlockType.ROBOT, t3));
    blocks.add(BlockFactory.build(BlockType.TRASH, t4));
    World world = new World(new Vec2());
    blocks.forEach(block -> block.link(world));
    Board board = BoardConverter.worldToBoard(blocks);

    Assertions.assertEquals(certifiedBoard, board);
  }

  @Test
  void testBoardToWorld() {
    Board.Builder builder = new Board.Builder(5, 1);
    TileVec2 t0 = TileVec2.of(0, 0);
    TileVec2 t1 = TileVec2.of(1, 0);
    TileVec2 t2 = TileVec2.of(2, 0);
    TileVec2 t3 = TileVec2.of(3, 0);
    TileVec2 t4 = TileVec2.of(4, 0);

    builder.setBlockTypeAt(t0, BlockType.WALL);
    builder.setBlockTypeAt(t1, BlockType.BOMB);
    builder.setBlockTypeAt(t2, BlockType.GARBAGE);
    builder.setBlockTypeAt(t3, BlockType.ROBOT);
    builder.setBlockTypeAt(t4, BlockType.TRASH);
    Board board = builder.build();

    List<Block> certifiedBlocks = new LinkedList<>();

    certifiedBlocks.add(BlockFactory.build(BlockType.WALL, t0));
    certifiedBlocks.add(BlockFactory.build(BlockType.BOMB, t1));
    certifiedBlocks.add(BlockFactory.build(BlockType.GARBAGE, t2));
    certifiedBlocks.add(BlockFactory.build(BlockType.ROBOT, t3));
    certifiedBlocks.add(BlockFactory.build(BlockType.TRASH, t4));

    List<Block> blocks = BoardConverter.boardToWorld(board);
    List<BlockType> blockTypes = blocks.stream().map(Block::getType).collect(Collectors.toList());
    List<BlockType> blockTypesCertified = certifiedBlocks.stream().map(Block::getType).collect(Collectors.toList());
    Assertions.assertEquals(blockTypesCertified, blockTypes);
  }

}
