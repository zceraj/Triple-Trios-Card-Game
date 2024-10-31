package tripleTrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * Test class for the HumanPlayer class.
 */
public class HumanPlayerTest {
  private HumanPlayer player;
  private Card card1;
  private Card card2;

  /**
   * Set up the test environment.
   */
  @BeforeEach
  public void setUp() {
    player = new HumanPlayer("Player1", PlayerColor.RED);
    card1 = new Card("Card 1", 1, 2, 3, 4);
    card2 = new Card("Card 2", 2, 4, 6, 8);
  }

  @Test
  public void testGetName() {
    assertEquals("Player1", player.getName());
  }

  @Test
  public void testGetColor() {
    assertEquals("Red", player.getColor());
    assertNotEquals("Blue", player.getColor());
  }

  @Test
  public void testAddCardToHand() {
    player.addCardToHand(card1);
    assertTrue(player.getHand().contains(card1));
  }

  @Test
  public void testRemoveCardFromHand() {
    player.addCardToHand(card1);
    player.removeCardFromHand(card1);
    assertFalse(player.getHand().contains(card1));
  }

  @Test
  public void testSetHand() {
    List<Card> newHand = new ArrayList<>();
    newHand.add(card1);
    newHand.add(card2);

    player.setHand(newHand);
    assertEquals(newHand, player.getHand());

    assertThrows(IllegalArgumentException.class, () -> player.setHand(null));
  }

  @Test
  public void testGetHand() {
    List<Card> hand = new ArrayList<>();
    hand.add(card1);
    hand.add(card2);

    player.setHand(hand);
    assertEquals(hand, player.getHand());
  }

  @Test
  public void testPlayCard() {
    player.addCardToHand(card1);
    player.addCardToHand(card2);

    Card playedCard = player.playCard(0);
    assertEquals(card1, playedCard);
    assertFalse(player.getHand().contains(card1));

    assertThrows(IndexOutOfBoundsException.class, () -> player.playCard(5));
  }

  @Test
  public void testPlaceTheCard() {
    player.addCardToHand(card1);
    player.placeTheCard(card1, 1, 1);

    assertEquals(1, card1.getRow());
    assertEquals(1, card1.getCol());

    assertThrows(IllegalArgumentException.class, () -> player.placeTheCard(null, 0, 0));
  }

}
