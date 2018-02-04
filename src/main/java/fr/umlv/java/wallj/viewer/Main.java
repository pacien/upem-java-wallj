package fr.umlv.java.wallj.viewer;

import fr.umlv.java.wallj.board.Board;
import fr.umlv.java.wallj.board.BoardParser;
import fr.umlv.java.wallj.board.BoardValidator;
import fr.umlv.zen5.Application;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The main class of the application.
 *
 * @author Adam NAILI
 * @author Pacien TRAN-GIRARD
 */
public final class Main {
  private static final String MAP_DIR_KEY = "levels";
  private static final String DEFAULT_MAP_DIR = "/maps";
  private static final String MAP_FILE_PATTERN = "level*.txt";
  private static final String JAR_SCHEME = "jar";

  private static Path getMapDirPath() {
    try {
      if (System.getProperty(MAP_DIR_KEY) != null) {
        return Paths.get(System.getProperty(MAP_DIR_KEY));
      } else {
        URI defaultMapDirUri = Main.class.getResource(DEFAULT_MAP_DIR).toURI();
        if (defaultMapDirUri.getScheme().equals(JAR_SCHEME))
          FileSystems.newFileSystem(defaultMapDirUri, Collections.emptyMap());

        return Paths.get(defaultMapDirUri);
      }
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Stream<Path> listMaps(Path mapDir) {
    try {
      return StreamSupport
             .stream(Files.newDirectoryStream(mapDir, MAP_FILE_PATTERN).spliterator(), false)
             .sorted((l, r) -> String.CASE_INSENSITIVE_ORDER.compare(l.toString(), r.toString()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Board loadBoard(Path path) {
    try {
      return BoardParser.parse(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Board validateBoard(Board board) {
    try {
      return new BoardValidator(board)
             .validate(BoardValidator.Constraint::hasActualReachableBlocks, "Some supposed reachable blocks are not reachable.")
             .validate(BoardValidator.Constraint::hasMandatoryBlocks, "Some mandatory blocks are missing.")
             .validate(BoardValidator.Constraint::isBounded, "The board is not correctly bounded.")
             .validate(BoardValidator.Constraint::isHollow, "The board must have a unique and simple interior.")
             .get();
    } catch (BoardValidator.ValidationException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    List<Board> levels = listMaps(getMapDirPath())
                         .map(Main::loadBoard)
                         .map(Main::validateBoard)
                         .collect(Collectors.toList());

    Viewer viewer = new Viewer(levels);
    Application.run(Color.WHITE, viewer::eventLoop);
  }

  private Main() {
    // static class
  }
}
