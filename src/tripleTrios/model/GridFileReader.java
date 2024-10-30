package tripleTrios.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GridFileReader {
  private boolean[][] grid;

  public GridFileReader(String filePath) throws IOException {
    readGridFile(filePath);
  }

  /**
   * Reads a grid configuration file and converts it to a boolean 2D array.
   * @param filename Path to the grid configuration file.
   * @return 2D boolean array representing the grid, where true is CardCell and false is Hole.
   * @throws IOException If there is an error reading the file.
   */
  protected void readGridFile(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      // Read the grid dimensions
      String[] dimensions = reader.readLine().split(" ");
      int rows = Integer.parseInt(dimensions[0]);
      int cols = Integer.parseInt(dimensions[1]);

      // Initialize the boolean array for grid configuration
      boolean[][] grid = new boolean[rows][cols];

      // Read each row in the grid
      for (int row = 0; row < rows; row++) {
        String line = reader.readLine();
        for (int col = 0; col < cols; col++) {
          grid[row][col] = line.charAt(col) == 'C'; // 'C' = true (CardCell), 'X' = false (Hole)
        }
      }

      this.grid= grid;
    }
  }

  public boolean[][] getGrid() {
    return grid;
  }
}

