package tripletrios.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests the card class of the model.
 */
public class CardTest {
  private Card card;

  /**
   * sets up the card class.
   */
  @BeforeEach
  public void setUp() {
    this.card = new Card("TestCard", 5, 3, 7, 1);
  }

  @Test
  public void testGetAttackValue() {
    assertEquals(5, this.card.getAttackValue(Direction.NORTH));
    assertEquals(3, this.card.getAttackValue(Direction.SOUTH));
    assertEquals(7, this.card.getAttackValue(Direction.EAST));
    assertEquals(1, this.card.getAttackValue(Direction.WEST));
  }

  @Test
  public void testGetRow() {
    assertEquals(2, card.getRow());
  }

}