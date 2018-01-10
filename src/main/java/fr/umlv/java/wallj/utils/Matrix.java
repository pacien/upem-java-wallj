package fr.umlv.java.wallj.utils;

import java.util.List;

/**
 * Utility functions for two dimension arrays and lists.
 *
 * @author Pacien TRAN-GIRARD
 */
public final class Matrix {

  /**
   * @param m the matrix (2D array)
   * @return the width of the matrix (0 if null)
   */
  public static int getWidth(Object[][] m) {
    return m != null && m.length > 0 ? m[0].length : 0;
  }

  /**
   * @param m the matrix (2D array)
   * @return the height of the matrix (0 if null)
   */
  public static int getHeight(Object[][] m) {
    return m != null ? m.length : 0;
  }

  /**
   * @param m the matrix (2D List)
   * @return the width of the matrix (0 if null)
   */
  public static int getWidth(List<? extends List<?>> m) {
    return m != null && m.size() > 0 ? m.get(0).size() : 0;
  }

  /**
   * @param m the matrix (2D List)
   * @return the height of the matrix (0 if null)
   */
  public static int getHeight(List<? extends List<?>> m) {
    return m != null ? m.size() : 0;
  }

  /**
   * @param l the 2D list to check
   * @return T(l is a valid matrix, i.e. all sub - arrays are of the same size and l is not null)
   */
  public static boolean isShapeValid(List<? extends List<?>> l) {
    return l != null && l.stream().mapToInt(List::size).allMatch(s -> s == l.get(0).size());
  }

  private Matrix() {
    // static class
  }

}
