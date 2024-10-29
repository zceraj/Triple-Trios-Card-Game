package tripleTrios.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tripleTrios.model.Grid;
import tripleTrios.model.Cell;
import tripleTrios.model.Card;

class GridTest {
  private Grid defaultGrid;
  private Grid customGrid;

  @BeforeEach
  void setUp() {
    defaultGrid = new Grid(3, 3);

    boolean[][] cellTypes = {
            {true, false},
            {false, true}
    };
    customGrid = new Grid(cellTypes);
  }

  @Test
  void testDefaultGridInitialization() {
    for (int row = 0; row < defaultGrid.getRows(); row++) {
      for (int col = 0; col < defaultGrid.getCols(); col++) {
        assertTrue(defaultGrid.getCell(row, col).isCardCell(),
                "Cell at (" + row + ", " + col + ") should be a CardCell");
      }
    }
  }

  @Test
  void testCustomGridInitialization() {
    assertTrue(customGrid.getCell(0, 0).isCardCell(), "Cell (0,0) should be a CardCell");
    assertFalse(customGrid.getCell(0, 1).isCardCell(), "Cell (0,1) should be a Hole");
    assertFalse(customGrid.getCell(1, 0).isCardCell(), "Cell (1,0) should be a Hole");
    assertTrue(customGrid.getCell(1, 1).isCardCell(), "Cell (1,1) should be a CardCell");
  }

  @Test
  void testGetCellWithinBounds() {
    assertNotNull(defaultGrid.getCell(1, 1));
  }

  @Test
  void testGetCellOutOfBounds() {
    assertThrows(IndexOutOfBoundsException.class, () -> defaultGrid.getCell(-1, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> defaultGrid.getCell(0, -1));
    assertThrows(IndexOutOfBoundsException.class, () -> defaultGrid.getCell(3, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> defaultGrid.getCell(0, 3));
  }

  @Test
  void testIsValidCell() {
    assertTrue(defaultGrid.isValidCell(0, 0));
    assertTrue(defaultGrid.isValidCell(2, 2));
    assertFalse(defaultGrid.isValidCell(-1, 0));
    assertFalse(defaultGrid.isValidCell(3, 3));
  }

  @Test
  void testGetCardAtCardCell() {
    Cell cell = defaultGrid.getCell(0, 0);
    Player player1 = new Player("Player1", PlayerColor.valueOf("Blue"));
    Card testCard = new Card("TestCard", 5, 3, 7, 1, player1, 2, 3);
    cell.setCard(testCard);
    assertEquals(testCard, defaultGrid.getCardAt(0, 0),
            "Expected card at (0,0) to match the test card");
  }

  @Test
  void testGetCardAtHoleOrEmptyCell() {
    assertNull(customGrid.getCardAt(0, 1), "Expected null at (0,1) as it is a Hole");
    assertNull(defaultGrid.getCardAt(1, 1), "Expected null at (1,1) as it has no card");
  }

  @Test
  void testGridDimensions() {
    assertEquals(3, defaultGrid.getRows(), "Default grid should have 3 rows");
    assertEquals(3, defaultGrid.getCols(), "Default grid should have 3 columns");
    assertEquals(2, customGrid.getRows(), "Custom grid should have 2 rows");
    assertEquals(2, customGrid.getCols(), "Custom grid should have 2 columns");
  }
}
