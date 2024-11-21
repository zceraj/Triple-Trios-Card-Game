package tripletrios.model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.Grid;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * tests the grid class of the model.
 */
public class GridTest {
  private Grid easyGrid;
  private Grid smallGrid;

  /**
   * sets up grids to be used in the tests.
   */
  @BeforeEach
  public void setUp() {
    boolean[][] grid = {
            {true, false, true},
            {false, true, true},
            {true, false, false}
    };
    easyGrid = new Grid(grid);

    boolean[][] grid2 = {
            {true, false, false},
            {false, true, true},
            {true, true, false}
    };
    smallGrid = new Grid(grid2);
  }


  @Test
  public void testDefaultGridInitialization() {
    assertFalse(easyGrid.getCell(0, 1).isCardCell());
  }

  @Test
  public void testCustomGridInitialization() {
    assertTrue(smallGrid.getCell(0, 0).isCardCell(), "Cell (0,0) should be a CardCell");
    assertFalse(smallGrid.getCell(0, 1).isCardCell(), "Cell (0,1) should be a Hole");
    assertFalse(smallGrid.getCell(1, 0).isCardCell(), "Cell (1,0) should be a Hole");
    assertTrue(smallGrid.getCell(1, 1).isCardCell(), "Cell (1,1) should be a CardCell");
  }

  @Test
  public void testGetCellWithinBounds() {
    assertNotNull(easyGrid.getCell(1, 1));
  }

  @Test
  public void testGetCellOutOfBounds() {
    assertThrows(IndexOutOfBoundsException.class, () -> easyGrid.getCell(-1, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> easyGrid.getCell(0, -1));
    assertThrows(IndexOutOfBoundsException.class, () -> easyGrid.getCell(3, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> easyGrid.getCell(0, 3));
  }

  @Test
  public void testIsValidCell() {
    assertTrue(easyGrid.isValidCell(0, 0));
    assertTrue(easyGrid.isValidCell(2, 2));
    assertFalse(easyGrid.isValidCell(-1, 0));
    assertFalse(easyGrid.isValidCell(3, 3));
  }

  @Test
  public void testGetCardAtCardCell() {
    Cell cell = easyGrid.getCell(0, 0);
    Card testCard = new Card("TestCard", 5, 3, 7, 1);
    cell.setCard(testCard);
    assertEquals(testCard, easyGrid.getCardAt(0, 0),
            "Expected card at (0,0) to match the test card");
  }

  @Test
  public void testGetCardAtHoleOrEmptyCell() {
    assertNull(smallGrid.getCardAt(0, 1), "Expected null at (0,1) as it is a Hole");
    assertNull(easyGrid.getCardAt(1, 1), "Expected null at (1,1) as it has no card");
  }

  @Test
  public void testGridDimensions() {
    assertEquals(3, easyGrid.getRows(), "Default grid should have 3 rows");
    assertEquals(3, easyGrid.getCols(), "Default grid should have 3 columns");
    assertEquals(3, smallGrid.getRows(), "Custom grid should have 3 rows");
    assertEquals(3, smallGrid.getCols(), "Custom grid should have 3 columns");
  }

  @Test
  public void testToString() {
    // Place cards in specific cells for testing
    smallGrid.getCell(0, 0).setCard(new Card("CardA", 4, 5, 6, 7));
    smallGrid.getCell(1, 1).setCard(new Card("CardB", 8, 9, 10, 5));

    // Define the expected string representation of the grid
    String expectedOutput =
            "[CardA] [ ] [ ]\n"
                    + "[ ] [CardB] [X]\n"
                    + "[X] [X] [ ]";

    // Test if the actual output matches the expected output
    assertEquals(expectedOutput, smallGrid.toString());
  }
}
