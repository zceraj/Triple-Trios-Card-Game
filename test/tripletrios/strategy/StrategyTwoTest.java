package tripletrios.strategy;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import cs3500.tripletrios.controller.GridFileReader;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.strategy.Moves;
import cs3500.tripletrios.strategy.StrategyTwo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StrategyTwoTest {

  private Grid testGrid;
  private MockPlayer mockPlayer;
  private MockModelStrategyTwo mockModel;
  private StrategyTwo strategyTwo;

  @Before
  public void setup() throws IOException {
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid5x5.txt");
    testGrid = new Grid(gridFileReader.getGrid());

    mockPlayer = new MockPlayer("Computer");
    mockPlayer.addCardToHand(new Card("Card1", 3, 2, 1, 5));  // Moderate attributes
    mockPlayer.addCardToHand(new Card("Card2", 6, 4, 3, 2));  // Higher attack value
    mockPlayer.addCardToHand(new Card("Card3", 1, 7, 2, 6));  // High defense, low attack value

    MockPlayer mockOpponent = new MockPlayer("Opponent");
    mockModel = new MockModelStrategyTwo(testGrid, mockPlayer, mockOpponent);

    strategyTwo = new StrategyTwo(mockModel);
  }

  //tests the getBestMove method for StrategyTwo
  @Test
  public void testGetBestMove() {
    Moves bestMove = strategyTwo.getBestMove(mockPlayer);

    CardInterface expectedCard = mockPlayer.getHand().get(2);
    //should be for top right corner
    int expectedRow = 0;
    int expectedCol = 4;

    assertNotNull("Best move should not be null.", bestMove);
    assertEquals("Expected card with the highest defense value to be chosen.", expectedCard, bestMove.getCard());
    assertEquals("Expected row to be 0.", expectedRow, bestMove.getRow());
    assertEquals("Expected col to be 4.", expectedCol, bestMove.getCol());
  }

  @Test
  public void testGetBestMoveWithTieBreaking() {
    mockPlayer.clearHand();
    mockPlayer.addCardToHand(new Card("Card1", 3, 2, 1, 5));
    mockPlayer.addCardToHand(new Card("Card2", 3, 2, 1, 5));

    Moves bestMove = strategyTwo.getBestMove(mockPlayer);

    CardInterface expectedCard = mockPlayer.getHand().get(0);
    assertNotNull("Best move should not be null.", bestMove);
    assertEquals("Expected card with the lowest index to be chosen in case of a tie.", expectedCard, bestMove.getCard());
  }


  @Test
  public void testMethodCallsForGetBestMove() {
    strategyTwo.getBestMove(mockPlayer);
    List<String> methodCalls = mockModel.getMethodCalls();
    boolean foundGameGrid = false;

    for (String methodCall : methodCalls) {
      if (methodCall.equals("getGameGrid")) {
        foundGameGrid = true;
        break;
      }
    }
    assertEquals("Expected method getGameGrid to be called at least once.", true, foundGameGrid);

  }

  @Test
  public void testGetBestMoveMaxDefense() {
    Moves bestMove = strategyTwo.getBestMove(mockPlayer);

    CardInterface expectedCard = mockPlayer.getHand().get(2);
    assertNotNull("Best move should not be null.", bestMove);
    assertEquals("Expected card with the highest defense value to be chosen.", expectedCard, bestMove.getCard());
  }
}
