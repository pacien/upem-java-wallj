package fr.umlv.java.wallj;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardParser;
import fr.umlv.java.wallj.viewer.Viewer;
import fr.umlv.zen5.Application;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {

  private static Path getResourcePath(String str) throws URISyntaxException {
    return Paths.get(Main.class.getResource(str).toURI());
  }

  private static java.util.List<Path> getResourcePaths(String[] args) throws URISyntaxException {
    LinkedList<Path> paths = new LinkedList<>();
    if (args.length > 0) {
      for (String string : args) {
        paths.add(Paths.get(string));
      }
    } else {
      paths.add(getResourcePath("/maps/smallvalid.txt"));
    }
    return paths;
  }

  public static void main(String[] args) {
    java.util.List<Board> boards = new LinkedList<>();
    try {
      List<Path> boardPaths = Main.getResourcePaths(args);
      for (Path path : boardPaths) {
        boards.add(BoardParser.parse(path));
      }
      Viewer viewer = new Viewer(boards);
      Application.run(Color.BLACK, viewer::eventLoop);
    } catch (URISyntaxException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(2);
    }
  }
}
