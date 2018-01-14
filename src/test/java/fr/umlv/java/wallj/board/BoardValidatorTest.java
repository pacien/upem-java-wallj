package fr.umlv.java.wallj.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Pacien TRAN-GIRARD
 */
final class BoardValidatorTest {

  private Path getResourcePath(String str) throws URISyntaxException {
    return Paths.get(getClass().getResource(str).toURI());
  }

  @Test
  void testConstraints() throws URISyntaxException, IOException {
    Board validBoard = BoardParser.parse(getResourcePath("/maps/bigValid.txt"));
    Assertions.assertTrue(BoardValidator.Constraint.isBounded(validBoard));
    Assertions.assertTrue(BoardValidator.Constraint.isHollow(validBoard));
    Assertions.assertTrue(BoardValidator.Constraint.hasActualReachableBlocks(validBoard));
    Assertions.assertTrue(BoardValidator.Constraint.hasMandatoryBlocks(validBoard));

    Board invalidBoard = BoardParser.parse(getResourcePath("/maps/bigInvalid.txt"));
    Assertions.assertFalse(BoardValidator.Constraint.isBounded(invalidBoard));
    Assertions.assertFalse(BoardValidator.Constraint.isHollow(invalidBoard));
    Assertions.assertFalse(BoardValidator.Constraint.hasActualReachableBlocks(invalidBoard));
    Assertions.assertFalse(BoardValidator.Constraint.hasMandatoryBlocks(invalidBoard));
  }

}
