package tripletrios.strategy;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.tripletrios.controller.GridFileReader;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.strategy.Moves;
import cs3500.tripletrios.strategy.StrategyOne;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StrategyOneTest {

  private MockModelStrategyOne mockModel;
  private StrategyOne strategyOne;
  private MockPlayer aiPlayer;


  @Before
  public void setup() throws IOException {
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid3x3.txt");
    Grid grid = new Grid(gridFileReader.getGrid());

    aiPlayer = new MockPlayer("AIPlayer");
    mockModel = new MockModelStrategyOne(grid, aiPlayer, aiPlayer);

    strategyOne = new StrategyOne(mockModel);
  }

  @Test
  public void testGetBestMove_MaximizeFlips() {
    Moves bestMove = strategyOne.getBestMove(aiPlayer);

    assertNotNull("Strategy should return a valid move", bestMove);

    int expectedFlips = calculateExpectedFlips(bestMove);
    assertTrue("The best move should maximize potential flips",
            expectedFlips > 0);

    assertTrue("getGameGrid should have been called",
            mockModel.getMethodCalls().contains("getGameGrid"));
    assertTrue("getCellsPlayer should have been called",
            mockModel.getMethodCalls().contains("getCellsPlayer"));
  }



}
