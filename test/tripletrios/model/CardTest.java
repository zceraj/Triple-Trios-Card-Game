package tripletrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the Card class of the model.
 */
public class CardTest {
  private Card card;

  /**
   * Sets up the Card instance for testing.
   */
  @BeforeEach
  public void setUp() {
    this.card = new Card("TestCard", 5, 3, 7, 1);
  }

  @Test
  public void testGetAttackValue() {
    assertEquals(
            "5", this.card.getAttackValue(Direction.NORTH),
            "Expected North attack value to be 5.");
    assertEquals(
            "3", this.card.getAttackValue(Direction.SOUTH),
            "Expected South attack value to be 3.");
    assertEquals(
            "7", this.card.getAttackValue(Direction.EAST),
            "Expected East attack value to be 7.");
    assertEquals(
            "1", this.card.getAttackValue(Direction.WEST),
            "Expected West attack value to be 1.");
  }

  @Test
  public void testGetRowAndColBeforePlacement() {
    assertEquals(
            -1, card.getRow(),
            "Expected default row to be -1 before placement.");
    assertEquals(
            -1, card.getCol(),
            "Expected default column to be -1 before placement.");
  }

  @Test
  public void testAddRowAndAddCol() {
    card.addRow(2);
    card.addCol(3);

    assertEquals(2, card.getRow(), "Expected row to be 2 after setting.");
    assertEquals(3, card.getCol(), "Expected column to be 3 after setting.");
  }

  @Test
  public void testMaxAttackValue() {
    Card highValueCard = new Card("HighValueCard", 10, 9, 8, 7);
    assertEquals(
            "A", highValueCard.getAttackValue(Direction.NORTH),
            "Expected North attack value to be 'A' for 10.");
    assertEquals(
            "9", highValueCard.getAttackValue(Direction.SOUTH),
            "Expected South attack value to be '9'.");
  }

  @Test
  public void testInvalidAttackValue() {
    assertThrows(IllegalArgumentException.class, () -> new Card(
            "InvalidCard", 0, 3, 4, 5),
            "Expected exception for attack value outside range 1-10.");
    assertThrows(IllegalArgumentException.class, () -> new Card(
            "InvalidCard", 11, 3, 4, 5),
            "Expected exception for attack value outside range 1-10.");
    assertThrows(IllegalArgumentException.class, () -> new Card("InvalidCard",
                    -1, 3, 4, 5),
            "Expected exception for negative attack value.");
  }

}
