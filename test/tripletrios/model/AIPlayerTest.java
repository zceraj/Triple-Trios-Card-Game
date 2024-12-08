package tripletrios.model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.controller.GridFileReader;
import cs3500.tripletrios.model.AiPlayer;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.strategy.Moves;
import cs3500.tripletrios.strategy.MovesInterface;
import cs3500.tripletrios.strategy.StrategyInterface;
import cs3500.tripletrios.strategy.StrategyOne;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the AI player.
 */
public class AIPlayerTest {

  private AiPlayer aiPlayer;
  private CardInterface card1;
  private MockModelAIPlayer mockModel;

  @Before
  public void setUp() throws IOException {
    // Initialize grid from file
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid3x3.txt");
    Grid grid = new Grid(gridFileReader.getGrid());

    IPlayer opponent = new HumanPlayer("Opponent", PlayerColor.RED);

    card1 = new Card("Card1", 4, 6, 2, 3);
    CardInterface card2 = new Card("Card2", 3, 7, 1, 5);
    CardInterface card3 = new Card("Card3", 5, 4, 3, 6);
    List<CardInterface> hand = new ArrayList<>();
    hand.add(card1);
    hand.add(card2);
    hand.add(card3);

    mockModel = new MockModelAIPlayer(grid, opponent, null);

    aiPlayer = new AiPlayer("AI", PlayerColor.BLUE);

    StrategyInterface strategy = new StrategyOne(mockModel);
    aiPlayer.setStrategy(strategy);

    aiPlayer.setCurrentHand(hand);
  }

  @Test
  public void testGetName() {
    assertEquals("AI", aiPlayer.getName());
  }

  @Test
  public void testGetColor() {
    assertEquals("Blue", aiPlayer.getColor());
  }

  @Test
  public void testAddCardToHand() {
    CardInterface newCard = new Card("Card4", 4, 6, 2, 3);
    aiPlayer.addCardToHand(newCard);
    assertTrue(aiPlayer.getCurrentHand().contains(newCard));
  }

  @Test
  public void testRemoveCardFromHand() {
    aiPlayer.removeCardFromHand(card1);
    assertFalse(aiPlayer.getCurrentHand().contains(card1));
  }

  @Test
  public void testSetHand() {
    List<CardInterface> newHand = new ArrayList<>();
    newHand.add(new Card("Card5", 7, 3, 8, 2));
    aiPlayer.setCurrentHand(newHand);
    assertEquals(1, aiPlayer.getCurrentHand().size());
    assertEquals("Card5", aiPlayer.getCurrentHand().get(0).getCardName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetHandNull() {
    aiPlayer.setCurrentHand(null);
  }

  @Test
  public void testSetStrategy() {
    StrategyInterface newStrategy = new StrategyOne(mockModel);
    aiPlayer.setStrategy(newStrategy);
    MovesInterface move = aiPlayer.determineNextMove();
    assertNotNull("Strategy should be set, and determineNextMove should work.", move);
  }

  @Test
  public void testDetermineNextMove() {
    MovesInterface move = aiPlayer.determineNextMove();
    assertNotNull("AI should determine a valid move.", move);
    assertTrue("Move must be from AI's hand.", aiPlayer.getCurrentHand().contains(move.getCard()));
  }

  @Test
  public void testPlaceTheCard() {
    card1 = new Card("Card1", 3, 4, 5, 2);
    aiPlayer.addCardToHand(card1);
    int initialHandSize = aiPlayer.getCurrentHand().size();

    MovesInterface bestMove = aiPlayer.determineNextMove();
    aiPlayer.placeTheCard(card1, bestMove.getRow(), bestMove.getCol());

    assertEquals(bestMove.getRow(), card1.getRow());
    assertEquals(bestMove.getCol(), card1.getCol());

    assertEquals(initialHandSize - 1, aiPlayer.getCurrentHand().size());
    assertFalse(aiPlayer.getCurrentHand().contains(card1));
  }
}
