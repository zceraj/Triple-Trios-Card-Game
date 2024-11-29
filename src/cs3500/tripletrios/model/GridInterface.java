package cs3500.tripletrios.model;

public interface GridInterface {

  /**
   * Retrieves a specific cell at the given row and column.
   *
   * @param row Row of the cell.
   * @param col Column of the cell.
   * @return The cell at (row, col).
   * @throws IndexOutOfBoundsException if the coordinates are out of bounds.
   */
  Cell getCell(int row, int col) throws IndexOutOfBoundsException;

  /**
   * Gets the adjacent cell from the given position in the specified direction.
   *
   * @param row       The current row position
   * @param col       The current column position
   * @param direction The direction to move
   * @return The adjacent cell if it exists, or null if out of bounds
   * @throws IndexOutOfBoundsException if the direction is invalid.
   */
  Cell getAdjacentCells(
          int row,
          int col,
          Direction direction) throws IndexOutOfBoundsException;

  /**
   * Checks if the cell at the given row and column is within the grid bounds.
   *
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return True if the cell is within the grid bounds, false otherwise.
   */
  boolean isValidCell(int row, int col);

  /**
   * Retrieves the card at a specific position if the cell is valid.
   *
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return The card in the cell at (row, col), or null if empty or a Hole.
   */
  CardInterface getCardAt(int row, int col);

  /**
   * Returns the number of rows in the grid.
   *
   * @return The number of rows in the grid.
   */
  int getRows();

  /**
   * Returns the number of columns in the grid.
   *
   * @return The number of columns in the grid.
   */
  int getCols();


  /**
   * Gets the number of cells in the grid.
   *
   * @return The number of cells in the grid.
   */
  int getCount();

  /**
   * Updates the cell at the specified row and column with the provided {@code newCell} value.
   *
   * @param row     the row index of the cell to update
   * @param col     the column index of the cell to update
   * @param newCell the new cell to place at the specified location
   * @throws IndexOutOfBoundsException if the specified row or column is outside the grid bounds
   */
  void updateCell(int row, int col, Cell newCell);

  /**
   * Transforms the grid's state into a string that the viewer can use.
   * We decided that it was best to show the entire name of the card at the spot rather than
   * just a character to avoid confusion and also did not include the player in the rendering of the
   * grid. We decided that this would be shown in the GUI more clearly and that for testing and
   * simple playing purposes this was enough.
   *
   * @return String rendering of the grid.
   */
  String toString();
}
