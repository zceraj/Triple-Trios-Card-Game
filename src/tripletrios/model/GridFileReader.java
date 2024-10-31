package tripletrios.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.InputMismatchException;

/**
 * The GridFileReader class is responsible for reading a grid configuration file
 * and converting it into a 2D boolean array representing the grid.
 * Each cell in the grid is represented by a boolean value, where true
 * indicates a card cell ('C') and false indicates a hole ('X').
 */
public class GridFileReader implements GridReader {
  private boolean[][] grid;

  /**
   * Constructs a GridFileReader and calls the method that reads the grid configuration
   * from the specified file path, setting it to the grid above.
   *
   * @param filePath the path to the grid configuration file.
   * @throws FileNotFoundException if the file cannot be found.
   * @throws InvalidPathException if the path format is invalid.
   * @throws InputMismatchException if the grid dimensions or cell values are invalid.
   * @throws IOException if an error occurs while reading the file.
   */
  public GridFileReader(String filePath) throws FileNotFoundException, InvalidPathException, InputMismatchException, IOException {
    readGridFile(filePath);
  }

  /**
   * Reads a grid configuration file and converts it to a boolean 2D array.
   * @param filename Path to the grid configuration file.
   * @throws FileNotFoundException if the file cannot be found.
   * @throws InvalidPathException if the path format is invalid.
   * @throws InputMismatchException if the grid dimensions or cell values are invalid.
   * @throws IOException if an error occurs while reading the file.
   */
  private void readGridFile(String filename) throws FileNotFoundException, InvalidPathException, InputMismatchException, IOException {
    File file = new File(filename);

    if (!file.isFile()) {
      throw new FileNotFoundException("File not found: " + filename);
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      // Read the grid dimensions
      String[] dimensions = reader.readLine().split(" ");
      if (dimensions.length != 2) {
        throw new InputMismatchException("Invalid grid dimensions in file header.");
      }

      int rows, cols;
      try {
        rows = Integer.parseInt(dimensions[0]);
        cols = Integer.parseInt(dimensions[1]);
      } catch (NumberFormatException e) {
        throw new InputMismatchException("Grid dimensions must be integers.");
      }
      if (rows % 2 == 0 || cols % 2 == 0) {
        throw new InputMismatchException("the grid cannot have an even number of cells");
      }

      // Initialize the boolean array for grid configuration
      boolean[][] grid = new boolean[rows][cols];

      // Read each row in the grid
      for (int row = 0; row < rows; row++) {
        String line = reader.readLine();
        if (line == null || line.length() != cols) {
          throw new InputMismatchException("Mismatch in row length at row " + row + ".");
        }

        for (int col = 0; col < cols; col++) {
          char cell = line.charAt(col);
          if (cell == 'C') {
            grid[row][col] = true; // 'C' = true (CardCell)
          } else if (cell == 'X') {
            grid[row][col] = false; // 'X' = false (Hole)
          } else {
            throw new InputMismatchException("Invalid cell character at row " + row + ", column " + col + ": " + cell);
          }
        }
      }

      this.grid = grid;
    }
  }

  /**
   * Retrieves the grid as a 2D boolean array.
   *
   * @return a 2D array of boolean values representing the grid.
   */
  public boolean[][] getGrid() {
    return grid;
  }
}