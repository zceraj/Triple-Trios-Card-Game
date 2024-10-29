package tripleTrios.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import java.awt.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
  private Player player;
  private Card card1;
  private Card card2;

  @BeforeEach
  void setUp() {
    player = new Player("Player1", PlayerColor.RED);
//    card1 = new Card("Card 1", 1);
//    card2 = new Card("Card 2", 2);
  }

  @Test
  public void testGetName() {
    assertEquals("Player1", player.getName());
  }

  @Test
  public void testGetColor() {
    assertEquals("RED", player.getColor());
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

//  @Test
//  public void testGetHand() {
//    player.addCardToHand(card1);
//    player.addCardToHand(card2);
//    List<> hand = player.getHand();
//    assertEquals(2, hand.size());
//    assertEquals(true, hand.contains(card1));
//    assertEquals(true, hand.contains(card2));
//  }

  @Test
  public void testPlayCard() {
    player.addCardToHand(card1);
    player.addCardToHand(card2);
    Card playedCard = player.playCard(0);
    assertEquals(card1, playedCard);
    assertFalse(player.getHand().contains(card1));
  }

  @Test
  public void testToString() {
    player.addCardToHand(card1);
    String expectedString = "Player1 (RED) - Hand: [" + card1 + "]";
    assertEquals(expectedString, player.toString());
  }
}