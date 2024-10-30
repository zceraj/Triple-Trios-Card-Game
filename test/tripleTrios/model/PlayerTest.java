package tripleTrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
  private HumanPlayer player;
  private Card card1;
  private Card card2;

  @BeforeEach
  void setUp() {
    player = new HumanPlayer("Player1", PlayerColor.RED);
    card1 = new Card("Card 1", 1, 2, 3, 4);  // Assign to class-level variables
    card2 = new Card("Card 2", 2, 4, 6, 8);  // Assign to class-level variables
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
  }

  @Test
  public void testPlayCard() {
    player.addCardToHand(card1);
    player.addCardToHand(card2);
    Card playedCard = player.playCard(0);
    assertEquals(card1, playedCard);
    assertFalse(player.getHand().contains(card1));
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
  public void testToString() {
    player.addCardToHand(card1);
    String expectedString = "Player1 (RED) - Hand: [" + card1 + "]";
    assertEquals(expectedString, player.toString());
  }
}
