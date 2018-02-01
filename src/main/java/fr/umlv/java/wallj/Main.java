package fr.umlv.java.wallj;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardParser;
import fr.umlv.java.wallj.board.BoardValidator;
import fr.umlv.java.wallj.viewer.Viewer;
import fr.umlv.zen5.Application;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

  private static final String DEFAULT_MAP_NAME = "/maps/level0.txt";
  private static java.util.List<Path> getResourcePaths(String[] args) throws URISyntaxException,IOException {
    LinkedList<Path> paths = new LinkedList<>();
    String strFolder = System.getProperty("levels");
    if (strFolder != null) {
      Path pathFolder = Paths.get(strFolder);
      Files.newDirectoryStream(pathFolder).forEach(paths::add);
    } else {
      final URI uri = Main.class.getResource(DEFAULT_MAP_NAME).toURI();
      Map<String, String> env = new HashMap<>();
      env.put("create", "true");
      FileSystem zipfs = FileSystems.newFileSystem(uri, env);
      paths.add(Paths.get(uri));
    }
    return paths;
  }

  // TODO: use System.getProperty("levels") to get the path to the level directory
  public static void main(String[] args) {
    List<Board> boards = new LinkedList<>();
    try {
      List<Path> boardPaths = Main.getResourcePaths(args);
      for (Path path : boardPaths) {
        BoardValidator boardValidator = new BoardValidator(BoardParser.parse(path));
        boards.add(
         boardValidator.validate(BoardValidator.Constraint::hasActualReachableBlocks, "Some supposed reachable blocks are not reachable.")
          .validate(BoardValidator.Constraint::hasMandatoryBlocks, "Some mandatory blocks are missing.")
          .validate(BoardValidator.Constraint::isBounded, "The board is not correctly bounded.")
          .validate(BoardValidator.Constraint::isHollow, "The board must have a unique and simple interior.")
          .get());
      }
      Viewer viewer = new Viewer(boards);
      Application.run(Color.WHITE, viewer::eventLoop);
    } catch (URISyntaxException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(2);
    } catch (BoardValidator.ValidationException e) {
      for (Throwable throwable : e.getSuppressed()) {
        System.err.println(throwable.getMessage());
      }
      System.exit(3);
    }
  }
}
