package fr.umlv.java.wallj.viewer;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardParser;
import fr.umlv.java.wallj.board.BoardValidator;
import fr.umlv.zen5.Application;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class Main {

  private static final String DEFAULT_MAP_NAME = "/maps/level0.txt";


  private static FileSystem fileSystemForContext(URI uri) throws URISyntaxException, IOException {
    boolean isInJar = Objects.equals(Main.class.getProtectionDomain().getCodeSource().getLocation().getProtocol(), "jar");
    if (isInJar) {//JAR from command line handling
      Map<String, String> env = new HashMap<>();
      env.put("create", "true");
      return FileSystems.newFileSystem(uri, env);
    }
    return FileSystems.getDefault();//IDE Handling
  }

  private static List<Path> getResourcePaths() throws URISyntaxException, IOException {
    final URI uri = Main.class.getResource(DEFAULT_MAP_NAME).toURI();
    LinkedList<Path> paths = new LinkedList<>();
    String strFolder = System.getProperty("levels");
    if (strFolder != null) {
      Path pathFolder = Paths.get(strFolder);
      Files.newDirectoryStream(pathFolder).forEach(paths::add);
    } else {
      FileSystem fileSystem = fileSystemForContext(uri);
      paths.add(Paths.get(uri));
    }
    return paths;
  }

  public static void main(String[] args) {
    List<Board> boards = new LinkedList<>();
    try {
      List<Path> boardPaths = Main.getResourcePaths();
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
