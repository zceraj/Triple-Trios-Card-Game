package tripleTrios.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridFileReaderTest {

  private final String validFilePath = "valid_grid.txt";
  private final String invalidFilePath = "invalidGrid.txt";
  private final String gridWithInvalidDimensions = "invalid_grid_dimensions";
  private final String inccorectGrid = "incorrect_char_in_grid.txt";

  // Helper method to create a file for testing
  private void createTestFile(String path, String content) throws IOException {
    try (FileWriter writer = new FileWriter(path)) {
      writer.write(content);
    }
  }

  @Test
  public void testReadValidGridFile() throws IOException {
    createTestFile(validFilePath, "3 3\nCXC\nCCC\nCXC");

    GridFileReader reader = new GridFileReader(validFilePath);
    boolean[][] grid = reader.getGrid();

    assertNotNull(grid);
    assertEquals(3, grid.length);
    assertEquals(3, grid[0].length);
    assertTrue(grid[0][0]); // C -> true
    assertFalse(grid[0][1]); // X -> false
    assertTrue(grid[1][1]); // C -> true
  }

  @Test
  public void testFileNotFound() {
    assertThrows(FileNotFoundException.class, () -> {
      new GridFileReader(invalidFilePath);
    });
  }

  @Test
  public void testInvalidPath() {
    assertThrows(FileNotFoundException.class, () -> {
      new GridFileReader(invalidFilePath);
    });
  }

  @Test
  public void testInvalidGridDimensions() {
    assertThrows(InputMismatchException.class, () -> {
      new GridFileReader(gridWithInvalidDimensions);
    });
  }

  @Test
  public void testInvalidCharactersInGrid(){
    assertThrows(InputMismatchException.class, () -> {
      new GridFileReader(inccorectGrid);
    });
  }

  // Clean up test files after tests
  @org.junit.jupiter.api.AfterEach
  public void cleanUp() {
    new File(validFilePath).delete();
    new File(invalidFilePath).delete();
  }
}
