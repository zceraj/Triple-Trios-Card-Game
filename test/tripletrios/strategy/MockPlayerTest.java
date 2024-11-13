package tripletrios.strategy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;

import static org.junit.Assert.assertEquals;

public class MockPlayerTest {


  @Test
  public void testAddCardToHand() {
    MockPlayer mockPlayer = new MockPlayer("TestPlayer");

    CardInterface card1 = new Card("Ace of Spades", 10, 5, 3, 7);
    mockPlayer.addCardToHand(card1);

    List<CardInterface> hand = mockPlayer.getHand();
    assertEquals(1, hand.size());
    assertEquals(card1, hand.get(0));
  }

  @Test
  public void testRemoveCardFromHand() {
    MockPlayer mockPlayer = new MockPlayer("TestPlayer");

    CardInterface card1 = new Card("Ace of Spades", 10, 5, 3, 7);
    mockPlayer.addCardToHand(card1);
    mockPlayer.removeCardFromHand(card1);

    List<CardInterface> hand = mockPlayer.getHand();
    assertEquals(0, hand.size());
  }

  @Test
  public void testSetHand() {
    MockPlayer mockPlayer = new MockPlayer("TestPlayer");

    List<CardInterface> playerHand = new ArrayList<>();
    playerHand.add(new Card("Ace of Spades", 10, 5, 3, 7));
    playerHand.add(new Card("King of Hearts", 9, 8, 6, 4));

    mockPlayer.setHand(playerHand);
    List<CardInterface> hand = mockPlayer.getHand();

    assertEquals(2, hand.size());
    assertEquals(playerHand.get(0), hand.get(0));
    assertEquals(playerHand.get(1), hand.get(1));
  }

  @Test
  public void testPlaceTheCard() {
    MockPlayer mockPlayer = new MockPlayer("TestPlayer");

    CardInterface card1 = new Card("Ace of Spades", 10, 5, 3, 7);
    mockPlayer.addCardToHand(card1);

    mockPlayer.placeTheCard(card1, 0, 0);
  }
}
