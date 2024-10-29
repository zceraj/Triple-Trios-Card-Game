package tripleTrios.model;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Grid {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;
  private final Map<Cell, Map<Direction, Cell>> adjacentCellMap;

  /**
   * Creates a new grid with specified cell types (CardCells or Holes).
   * @param cellTypes 2D array of booleans indicating if a cell is a CardCell (true) or a Hole (false).
   */
  public Grid(boolean[][] cellTypes) {
    this.rows = cellTypes.length;
    this.cols = cellTypes[0].length;
    this.grid = new Cell[rows][cols];
    this.adjacentCellMap = new HashMap<>();

    // Initialize cells in grid
    initializeCells(cellTypes);
    trackCellsNextTo();
  }

  /**
   * Initializes cells as CardCells or Holes based on the cellTypes configuration array.
   * @param cellTypes 2D boolean array where true means CardCell and false means Hole.
   */
  private void initializeCells(boolean[][] cellTypes) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new Cell(row, col, cellTypes[row][col]);
      }
    }
  }

  /**
   * Tracks adjacent cells for each cell in the grid and stores them in adjacentCellMap.
   */
  private void trackCellsNextTo() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Cell cell = grid[row][col];
        Map<Direction, Cell> neighbors = new EnumMap<>(Direction.class);

        if (isValidCell(row - 1, col)) neighbors.put(Direction.NORTH, grid[row - 1][col]);
        if (isValidCell(row + 1, col)) neighbors.put(Direction.SOUTH, grid[row + 1][col]);
        if (isValidCell(row, col - 1)) neighbors.put(Direction.WEST, grid[row][col - 1]);
        if (isValidCell(row, col + 1)) neighbors.put(Direction.EAST, grid[row][col + 1]);

        adjacentCellMap.put(cell, neighbors);
      }
    }
  }

  /**
   * Retrieves a specific cell at the given row and column.
   * @param row Row of the cell.
   * @param col Column of the cell.
   * @return The cell at (row, col).
   * @throws IndexOutOfBoundsException if the coordinates are out of bounds.
   */
  public Cell getCell(int row, int col) {
    if (isValidCell(row, col)) {
      return grid[row][col];
    } else {
      throw new IndexOutOfBoundsException("Invalid cell coordinates.");
    }
  }

  /**
   * Retrieves the adjacent cells for a specific cell at (row, col).
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return Map of adjacent cells, keyed by direction.
   */
  public Map<Direction, Cell> getAdjacentCells(int row, int col) {
    Cell cell = getCell(row, col);
    return adjacentCellMap.getOrDefault(cell, new EnumMap<>(Direction.class));
  }

  /**
   * Checks if the cell at the given row and column is within the grid bounds.
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return True if the cell is within the grid bounds, false otherwise.
   */
  public boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  /**
   * Retrieves the card at a specific position if the cell is valid.
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return The card in the cell at (row, col), or null if empty or a Hole.
   */
  public Card getCardAt(int row, int col) {
    Cell cell = getCell(row, col);
    return cell.isCardCell() && !cell.isEmpty() ? cell.getCard() : null;
  }

  /**
   * Returns the number of rows in the grid.
   * @return The number of rows in the grid.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Returns the number of columns in the grid.
   * @return The number of columns in the grid.
   */
  public int getCols() {
    return cols;
  }
}
