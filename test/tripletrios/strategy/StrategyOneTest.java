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
import cs3500.tripletrios.strategy.StrategyOne;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StrategyOneTest {

  private Grid testGrid;
  private MockPlayer mockPlayer;
  private MockModelStrategyOne mockModel;
  private StrategyOne strategyOne;

  @Before
  public void setup() throws IOException {
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid3x3.txt");
    testGrid = new Grid(gridFileReader.getGrid());

    mockPlayer = new MockPlayer("Computer");
    mockPlayer.addCardToHand(new Card("Card1", 9, 9, 8, 10));
    mockPlayer.addCardToHand(new Card("Card2", 7, 3, 6, 4));
    mockPlayer.addCardToHand(new Card("Card3", 1, 10, 3, 2));

    MockPlayer mockOpponent = new MockPlayer("Opponent");
    mockModel = new MockModelStrategyOne(testGrid, mockPlayer, mockOpponent);

    strategyOne = new StrategyOne(mockModel);
  }

  @Test
  public void testGetBestMoveMaxFlips() {
    Moves bestMove = strategyOne.getBestMove(mockPlayer);

    CardInterface expectedCard = mockPlayer.getHand().get(0);
    int expectedRow = 0;
    int expectedCol = 0;

    assertNotNull("Best move should not be null.", bestMove);
    assertEquals("Expected card with the highest number of flips to be chosen.", expectedCard, bestMove.getCard());
    assertEquals("Expected row to be 0.", expectedRow, bestMove.getRow());
    assertEquals("Expected col to be 0.", expectedCol, bestMove.getCol());
  }


  @Test
  public void testGetBestMoveTieBreaking() {
    mockPlayer.addCardToHand(new Card("Card4", 7, 7, 7, 7));

    Moves bestMove = strategyOne.getBestMove(mockPlayer);

    CardInterface expectedCard = mockPlayer.getHand().get(0);  // Card1 should be chosen if there is a tie
    int expectedRow = 0;
    int expectedCol = 0;

    assertEquals("Tie should be resolved using the card with the lowest index.", expectedCard, bestMove.getCard());
    assertEquals("Tie should be resolved using the uppermost row.", expectedRow, bestMove.getRow());
    assertEquals("Tie should be resolved using the leftmost col.", expectedCol, bestMove.getCol());
  }
  @Test
  public void testMethodCallsForGetBestMove() {
    strategyOne.getBestMove(mockPlayer);

    List<String> methodCalls = mockModel.getMethodCalls();

    boolean foundGameGrid = false;
    boolean foundCurPlayerAfterGameGrid = false;

    for (String method : methodCalls) {
      if (method.equals("getGameGrid")) {
        foundGameGrid = true;
      }
      if (foundGameGrid && method.equals("getCurPlayer")) {
        foundCurPlayerAfterGameGrid = true;
        break;
      }
    }

    assertEquals("Expected method getGameGrid to be called at least once.", true, foundGameGrid);
    assertEquals("Expected method getCurPlayer to be called after at least one getGameGrid.", true, foundCurPlayerAfterGameGrid);
  }

}
