package fr.umlv.java.wallj.board;

import fr.umlv.java.wallj.block.BlockType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * Board deserializer.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class BoardParser {
  private static Board buildBoard(List<List<BlockType>> map) {
    if (!Matrix.isShapeValid(map)) throw new IllegalArgumentException("Board must be rectangular.");

    Board.Builder b = new Board.Builder(Matrix.getWidth(map), Matrix.getHeight(map));
    for (ListIterator<List<BlockType>> line = map.listIterator(); line.hasNext(); )
      for (ListIterator<BlockType> block = line.next().listIterator(); block.hasNext(); )
        b.setBlockTypeAt(TileVec2.of(block.nextIndex(), line.previousIndex()), block.next());

    return b.build();
  }

  private static BlockType parseChar(int c) {
    switch (c) {
      case ' ':
        return BlockType.FREE;
      case 'W':
        return BlockType.WALL;
      case 'T':
        return BlockType.TRASH;
      case 'G':
        return BlockType.GARBAGE;
      default:
        return null;
    }
  }

  private static List<BlockType> parseLine(String line) {
    return line.chars()
           .mapToObj(BoardParser::parseChar)
           .collect(Collectors.toList());
  }

  /**
   * Parses a block from a file.
   *
   * @param filePath path to the map file
   * @return the parsed Board
   * @throws IOException any IO exception that happened while reading the file
   */
  public static Board parse(Path filePath) throws IOException {
    return buildBoard(Files.lines(filePath)
                      .filter(s -> !s.isEmpty())
                      .map(BoardParser::parseLine)
                      .collect(Collectors.toList()));
  }

  private BoardParser() {
    // static class
  }
}
