package tripleTrios.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
  private Card card;
  private Player player1;
  private Player player2;

  @BeforeEach
  public void setUp() {
    this.player1 = new Player("Player1", PlayerColor.valueOf("Blue"));
    this.player2 = new Player("Player2", PlayerColor.valueOf("Red"));
    this.card = new Card("TestCard", 5, 3, 7, 1, player1, 2, 3);
  }

  @Test
  public void testGetAttackValue() {
    assertEquals(5, this.card.getAttackValue(Direction.NORTH));
    assertEquals(3, this.card.getAttackValue(Direction.SOUTH));
    assertEquals(7, this.card.getAttackValue(Direction.EAST));
    assertEquals(1, this.card.getAttackValue(Direction.WEST));
  }

  @Test
  public void testGetOwner() {
    assertEquals(player1, card.getOwner());
  }

  @Test
  public void testSetOwner() {
    card.setOwner(player2);
    assertEquals(player2, card.getOwner());
  }

  @Test
  public void testGetRow() {
    assertEquals(2, card.getRow());
  }


}