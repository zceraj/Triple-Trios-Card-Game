package tripletrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.PlayerColor;

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
    assertTrue(player.getCurrentHand().contains(card1));
  }

  @Test
  public void testRemoveCardFromHand() {
    player.addCardToHand(card1);
    player.removeCardFromHand(card1);
    assertFalse(player.getCurrentHand().contains(card1));
  }

  @Test
  public void testSetHand() {
    List<CardInterface> newHand = new ArrayList<>();
    newHand.add(card1);
    newHand.add(card2);

    player.setCurrentHand(newHand);
    assertEquals(newHand, player.getCurrentHand());

    assertThrows(IllegalArgumentException.class, () -> player.setCurrentHand(null));
  }

  @Test
  public void testGetHand() {
    List<CardInterface> hand = new ArrayList<>();
    hand.add(card1);
    hand.add(card2);

    player.setCurrentHand(hand);
    assertEquals(hand, player.getCurrentHand());
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
