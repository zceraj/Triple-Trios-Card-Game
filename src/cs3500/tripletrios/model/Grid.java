package cs3500.tripletrios.model;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Represents the grid of the game.
 * The grid is a 2D array of cells, where each cell can be a CardCell or a Hole.
 * The grid is tracked as a coordinate system with (0, 0) as the top-left corner.
 */
public class Grid implements GridInterface {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;
  private final Map<CellInterface, Map<Direction, CellInterface>> adjacentCellMap;

  /**
   * Creates a new grid with specified cell types (CardCells or Holes).
   *
   * @param cellTypes 2D array of booleans indicating if a cell is a CardCell or a Hole.
   */
  public Grid(boolean[][] cellTypes) {
    if (cellTypes.length % 2 == 0 || cellTypes[0].length % 2 == 0) {
      throw new InputMismatchException("the grid cannot have an even number of cells");
    }
    this.rows = cellTypes.length;
    this.cols = cellTypes[0].length;
    this.grid = new Cell[rows][cols];
    this.adjacentCellMap = new HashMap<>();
    initializeCells(cellTypes);
    trackCellsNextTo();
  }

  /**
   * Copy constructor for a new grid.
   *
   * @param original The grid to copy.
   */
  public Grid(Grid original) {
    this.rows = original.rows;
    this.cols = original.cols;
    this.grid = new Cell[rows][cols];
    this.adjacentCellMap = new HashMap<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        this.grid[row][col] = new Cell(original.grid[row][col]);
      }
    }
    trackCellsNextTo();
  }

  /**
   * Initializes cells as CardCells or Holes based on the cellTypes configuration array.
   *
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
        CellInterface cell = grid[row][col];
        Map<Direction, CellInterface> neighbors = new EnumMap<>(Direction.class);

        if (isValidCell(row - 1, col)) {
          neighbors.put(Direction.NORTH, grid[row - 1][col]);
        }

        if (isValidCell(row + 1, col)) {
          neighbors.put(Direction.SOUTH, grid[row + 1][col]);
        }

        if (isValidCell(row, col - 1)) {
          neighbors.put(Direction.WEST, grid[row][col - 1]);
        }

        if (isValidCell(row, col + 1)) {
          neighbors.put(Direction.EAST, grid[row][col + 1]);
        }

        adjacentCellMap.put(cell, neighbors);
      }
    }
  }


  /**
   * Retrieves a specific cell at the given row and column.
   *
   * @param row Row of the cell.
   * @param col Column of the cell.
   * @return The cell at (row, col).
   * @throws IndexOutOfBoundsException if the coordinates are out of bounds.
   */
  @Override
  public CellInterface getCell(int row, int col) {
    if (isValidCell(row, col)) {
      CellInterface originalCell = grid[row][col];
      return new Cell(originalCell);
    } else {
      throw new IndexOutOfBoundsException("Invalid cell coordinates.");
    }
  }

  /**
   * Gets the adjacent cell from the given position in the specified direction.
   *
   * @param row       The current row position
   * @param col       The current column position
   * @param direction The direction to move
   * @return The adjacent cell if it exists, or null if out of bounds
   * @throws IndexOutOfBoundsException if the direction is invalid.
   */
  @Override
  public CellInterface getAdjacentCells(
          int row,
          int col,
          Direction direction) throws IndexOutOfBoundsException {
    int newRow = row;
    int newCol = col;

    switch (direction) {
      case NORTH:
        newRow -= 1;
        break;
      case SOUTH:
        newRow += 1;
        break;
      case EAST:
        newCol += 1;
        break;
      case WEST:
        newCol -= 1;
        break;
      default:
        throw new IndexOutOfBoundsException("Invalid direction.");
    }

    if (isValidCell(newRow, newCol)) {
      return getCell(newRow, newCol);
    } else {
      return null;
    }
  }

  /**
   * Checks if the cell at the given row and column is within the grid bounds.
   *
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return True if the cell is within the grid bounds, false otherwise.
   */
  @Override
  public boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  /**
   * Retrieves the card at a specific position if the cell is valid.
   *
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return The card in the cell at (row, col), or null if empty or a Hole.
   */
  @Override
  public CardInterface getCardAt(int row, int col) {
    CellInterface cell = getCell(row, col);
    return cell.isCardCell() && !cell.isEmpty() ? cell.getCard() : null;
  }

  /**
   * Returns the number of rows in the grid.
   *
   * @return The number of rows in the grid.
   */
  @Override
  public int getRows() {
    return rows;
  }

  /**
   * Returns the number of columns in the grid.
   *
   * @return The number of columns in the grid.
   */
  @Override
  public int getCols() {
    return cols;
  }


  /**
   * Gets the number of cells in the grid.
   *
   * @return The number of cells in the grid.
   */
  @Override
  public int getCount() {
    int count = 0;
    for (int row = 0; row < this.getRows(); row++) {
      for (int col = 0; col < this.getCols(); col++) {
        if (this.getCell(row, col).isCardCell()) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Updates the cell at the specified row and column with the provided {@code newCell} value.
   *
   * @param row     the row index of the cell to update
   * @param col     the column index of the cell to update
   * @param newCell the new cell to place at the specified location
   * @throws IndexOutOfBoundsException if the specified row or column is outside the grid bounds
   */
  @Override
  public void updateCell(int row, int col, CellInterface newCell) {
    if (isValidCell(row, col)) {
      grid[row][col] = new Cell(newCell);
    } else {
      throw new IndexOutOfBoundsException("Invalid cell coordinates.");
    }
  }

  /**
   * Transforms the grid's state into a string that the viewer can use.
   * We decided that it was best to show the entire name of the card at the spot rather than
   * just a character to avoid confusion and also did not include the player in the rendering of the
   * grid. We decided that this would be shown in the GUI more clearly and that for testing and
   * simple playing purposes this was enough.
   *
   * @return String rendering of the grid.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int columns = 0; columns < grid.length; columns++) {
      for (int rows = 0; rows < grid[columns].length; rows++) {
        Cell cell = grid[columns][rows];
        if (cell.isCardCell() && cell.isEmpty()) {
          sb.append("[X]");
        } else if (!cell.isEmpty()) {
          sb.append("[").append(cell.getCard().getCardName()).append("]");
        } else {
          sb.append("[ ]");
        }
        if (rows < grid[columns].length - 1) {
          sb.append(" ");
        }
      }
      if (columns < grid.length - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}