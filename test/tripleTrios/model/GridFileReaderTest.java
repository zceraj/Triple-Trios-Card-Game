package tripleTrios.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridFileReaderTest {

  private final String validFilePath = "src/tripleTrios/TESTINGFILES/valid_grid.txt";
  private final String invalidFilePath = "invalidGrid.txt";
  private final String gridWithInvalidDimensions = "."+ File.separator +"TESTINGFILES/incorrect_grid_dimensions";
  private final String incorrectGrid = "incorrect_char_in_grid.txt";


  @Test
  public void testReadValidGridFile() throws IOException {
    GridFileReader reader = new GridFileReader(validFilePath);
    boolean[][] grid = reader.getGrid();

    assertNotNull(grid);
    assertEquals(3, grid.length);
    assertEquals(3, grid[0].length);
    assertTrue(grid[0][0]);
    assertFalse(grid[0][1]);
    assertTrue(grid[1][1]);
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
      new GridFileReader(incorrectGrid);
    });
  }

  // Clean up test files after tests
  @org.junit.jupiter.api.AfterEach
  public void cleanUp() {
    new File(validFilePath).delete();
    new File(invalidFilePath).delete();
  }
}
