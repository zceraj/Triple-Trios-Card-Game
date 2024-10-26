package tripleTrios.model;

/**
 * Represents the game board storing a list of cells.
 */
public class Grid {
  private int rows;
  private int cols;
  //stores a list of Cell objects that represent each space on the grid
  private Cell[][] grid;

  // constructor
  public Grid(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new Cell[rows][cols];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new Cell(row, col);
      }
    }
  }

  // get cell
  public Cell getCell(int row, int col) {
    if (isValidCell(row, col)) {
      return grid[row][col];
    } else {
      throw new IndexOutOfBoundsException("Invalid cell");
    }
  }

  private boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  // to string
}