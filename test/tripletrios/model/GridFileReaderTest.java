package tripletrios.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridFileReaderTest {

  @Test
  public void testReadValidGridFile() throws IOException {
    GridFileReader reader = new GridFileReader(
            "."+ File.separator +"TESTINGFILES"+ File.separator +"valid_grid.text");
    boolean[][] grid = reader.getGrid();

    assertNotNull(grid);
    assertEquals(3, grid.length);
    assertEquals(3, grid[0].length);
    assertTrue(grid[0][0]);
    assertFalse(grid[0][1]);
  }

  @Test
  public void testFileNotFound() {
    assertThrows(FileNotFoundException.class, () -> {
      new GridFileReader("invalidGrid.txt");
    });
  }

  @Test
  public void testInvalidPath() {
    assertThrows(FileNotFoundException.class, () -> {
      new GridFileReader("invalidGrid.txt");
    });
  }

  @Test
  public void testInvalidGridDimensions() {
    assertThrows(InputMismatchException.class, () -> {
      new GridFileReader("."+ File.separator +"TESTINGFILES"+ File.separator +"incorrect_grid_dimensions");
    });
  }

  @Test
  public void testInvalidCharactersInGrid(){
    assertThrows(InputMismatchException.class, () -> {
      new GridFileReader("."+ File.separator +"TESTINGFILES"+ File.separator +"incorrect_char_in_grid");
    });
  }
}
