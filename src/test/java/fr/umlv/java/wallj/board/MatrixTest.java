package fr.umlv.java.wallj.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Pacien TRAN-GIRARD
 */
final class MatrixTest {

  @Test
  void testMatrixSize() {
    Integer[][] ma = {{0}, {0}};
    Assertions.assertEquals(Matrix.getWidth(ma), 1);
    Assertions.assertEquals(Matrix.getHeight(ma), 2);

    List<List<Integer>> ml = Arrays.asList(Collections.singletonList(0), Collections.singletonList(0));
    Assertions.assertEquals(Matrix.getWidth(ml), 1);
    Assertions.assertEquals(Matrix.getHeight(ml), 2);
    Assertions.assertTrue(Matrix.isShapeValid(ml));
  }

}
