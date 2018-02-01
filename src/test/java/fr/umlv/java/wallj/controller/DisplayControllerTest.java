package fr.umlv.java.wallj.controller;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.TileVec2;
import fr.umlv.java.wallj.context.Context;
import fr.umlv.java.wallj.context.Game;
import fr.umlv.java.wallj.context.ScreenManager;
import fr.umlv.java.wallj.block.Block;
import fr.umlv.java.wallj.block.BlockFactory;
import fr.umlv.java.wallj.block.BlockType;
import fr.umlv.zen5.Application;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;

public class DisplayControllerTest {
  @Test
  void TestDisplay() throws java.lang.InterruptedException {

    Application.run(Color.WHITE, applicationContext -> {
      for (; ; ) {
        applicationContext.renderFrame(graphics2D -> {
          Board.Builder builder = new Board.Builder(51, 51);
          TileVec2 t0 = TileVec2.of(50, 50);
          builder.setBlockTypeAt(t0, BlockType.WALL);
          LinkedList<Board> boards = new LinkedList<>();
          boards.add(builder.build());
          Game game = new Game(boards);
          ScreenManager screenManager = new ScreenManager(applicationContext, graphics2D);
          try {
            Thread.sleep(50);
          } catch (Exception e) {
            System.exit(-1);
          }
          Context context = new Context(game, Collections.emptyList(), screenManager.clearScreen());
          Block block = BlockFactory.build(BlockType.BOMB, t0);
          block.update(context);
        });
      }
    });
  }
}
