package tripleTrios.model;

/**
 * Represents the game board storing a list of cells.
 */
public class Grid {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;

  /**
   * Creates a new grid with the given number of rows and columns.
   * @param rows The number of rows in the grid.
   * @param cols The number of columns in the grid.
   */
  public Grid(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new Cell[rows][cols];

    // Initialize all cells as CardCells by default
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new Cell(row, col, true); // true indicates it's a CardCell
      }
    }
  }

  /**
   * Creates a grid with specified cell types (CardCells or Holes).
   * @param cellTypes 2D array of booleans indicating if a cell is a CardCell (true) or a Hole (false).
   */
  public Grid(boolean[][] cellTypes) {
    this.rows = cellTypes.length;
    this.cols = cellTypes[0].length;
    this.grid = new Cell[rows][cols];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new Cell(row, col, cellTypes[row][col]);
      }
    }
  }

  // Retrieves a cell at a specific location
  public Cell getCell(int row, int col) {
    if (isValidCell(row, col)) {
      return grid[row][col];
    } else {
      throw new IndexOutOfBoundsException("Invalid cell");
    }
  }

  // Checks if the cell based on its row and column is within grid bounds

  /**
   * Checks if the cell at the given row and column is within the grid bounds.
   * @param row The row of the cell
   * @param col The column of the cell
   * @return True if the cell is within the grid bounds, false otherwise
   */
  public boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  // Retrieves the card at a specific position if the cell is valid
  public Card getCardAt(int row, int col) {
    Cell cell = getCell(row, col);
    return cell.isCardCell() && !cell.isEmpty() ? cell.getCard() : null;
  }

  // Returns the number of rows in the grid
  public int getRows() {
    return rows;
  }

  // Returns the number of columns in the grid
  public int getCols() {
    return cols;
  }

}
