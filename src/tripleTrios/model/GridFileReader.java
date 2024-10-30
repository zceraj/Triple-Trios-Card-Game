package tripleTrios.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GridFileReader {
  private Grid grid;

  public GridFileReader(String filePath) throws IOException {
    parseGridFile(filePath);
  }

  private void parseGridFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String[] dimensions = reader.readLine().split(" ");
      int rows = Integer.parseInt(dimensions[0]);
      int cols = Integer.parseInt(dimensions[1]);

      grid = new boolean[][];

      for (int i = 0; i < rows; i++) {
        String line = reader.readLine();
        for (int j = 0; j < cols; j++) {
          grid[i][j] = line.charAt(j);
        }
      }
    } catch (IOException | NumberFormatException | NullPointerException e) {
      System.err.println("Error reading grid configuration file: " + e.getMessage());
      throw e; // Optionally rethrow or handle as needed
    }
  }

  public Grid getGrid() {
    return grid;
  }
}

