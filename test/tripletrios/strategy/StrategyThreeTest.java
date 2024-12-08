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
import cs3500.tripletrios.strategy.StrategyThree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StrategyThree class in the Triple Trios game.
 * These tests verify the functionality of the strategy, including the selection of optimal moves
 * based on game logic and strategy implementation.
 */
public class StrategyThreeTest {

  private Grid testGrid;
  private MockPlayer mockPlayer;
  private MockModelStrategyThree mockModel;
  private StrategyThree strategyThree;

  @Before
  public void setup() throws IOException {
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid7x7.txt");
    testGrid = new Grid(gridFileReader.getGrid());

    mockPlayer = new MockPlayer("Computer");
    mockPlayer.addCardToHand(new Card("Card1", 3, 2, 1, 5));
    mockPlayer.addCardToHand(new Card("Card2", 6, 4, 3, 2));
    mockPlayer.addCardToHand(new Card("Card3", 1, 7, 2, 6));

    MockPlayer mockOpponent = new MockPlayer("Opponent");
    mockModel = new MockModelStrategyThree(testGrid, mockPlayer, mockOpponent);

    strategyThree = new StrategyThree(mockModel);
  }

  @Test
  public void testGetBestMove() {
    Moves bestMove = strategyThree.getBestMove(mockPlayer);

    assertNotNull("Best move should not be null.", bestMove);

    int row = bestMove.getRow();
    int col = bestMove.getCol();

    assertTrue("Expected chosen cell to be empty.", testGrid.getCell(row, col).isEmpty());

    CardInterface card = bestMove.getCard();
    int expectedRow = 0;
    int expectedCol = 0;

    assertEquals("Expected row to be 0.", expectedRow, row);
    assertEquals("Expected column to be 0.", expectedCol, col);

    assertEquals(
            "Expected Card1 to be selected as the best move.",
            mockPlayer.getCurrentHand().get(0), card);

  }

  @Test
  public void testMethodCallsForGetBestMove() {
    strategyThree.getBestMove(mockPlayer);

    List<String> methodCalls = mockModel.getMethodCalls();

    boolean foundGameGrid = false;

    for (String method : methodCalls) {
      if (method.equals("getGameGrid")) {
        foundGameGrid = true;
        break;
      }
    }

    assertTrue("Expected method getGameGrid to be called at least once.", foundGameGrid);
  }

  @Test
  public void testTieBreaking() {
    // Set up a scenario where two cards have the same evaluation score
    Moves bestMove = strategyThree.getBestMove(mockPlayer);

    // Since Card1 and Card2 have the same score, the tie-breaking should select Card1
    CardInterface expectedCard = mockPlayer.getCurrentHand().get(0);
    int expectedRow = 0;
    int expectedCol = 0;

    assertNotNull("Best move should not be null.", bestMove);
    assertEquals("Tie should be resolved using the card with the lowest index.",
            expectedCard, bestMove.getCard());
    assertEquals("Expected row to be 0.", expectedRow, bestMove.getRow());
    assertEquals("Expected column to be 0.", expectedCol, bestMove.getCol());
  }


}
