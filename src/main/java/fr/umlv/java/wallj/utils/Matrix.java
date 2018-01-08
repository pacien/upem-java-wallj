package fr.umlv.java.wallj.utils;

/**
 * Utility functions for two dimension arrays.
 */
public final class Matrix {

  /**
   * @param m the matrix
   * @return the width of the matrix (0 if null)
   */
  public static int getWidth(Object[][] m) {
    return m != null && m.length > 0 ? m[0].length : 0;
  }

  /**
   * @param m the matrix
   * @return the height of the matrix (0 if null)
   */
  public static int getHeight(Object[][] m) {
    return m != null ? m.length : 0;
  }

  private Matrix() {
    // static class
  }

}
