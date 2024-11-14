package tripletrios.strategy;

import cs3500.tripletrios.controller.GridFileReader;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;
import cs3500.tripletrios.strategy.ChainStrategy;
import cs3500.tripletrios.strategy.Moves;
import cs3500.tripletrios.strategy.StrategyFour;
import cs3500.tripletrios.strategy.StrategyInterface;
import cs3500.tripletrios.strategy.StrategyThree;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the ChainStrategy class.
 */
public class ChainStrategyTest {
  private Grid grid;
  private IPlayer mockPlayer;
  private IPlayer mockOpponent;

  @Before
  public void setUp() throws IOException {
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid3x3.txt");
    grid = new Grid(gridFileReader.getGrid());

    mockPlayer = new MockPlayer("Player 1");
    mockOpponent = new MockPlayer("Player 2");

    List<CardInterface> hand = new ArrayList<>();
    hand.add(new Card("Card1", 3, 2, 1, 5));
    hand.add(new Card("Card2", 6, 4, 3, 2));
    mockPlayer.setHand(hand);
  }

  @Test
  public void testChainStrategy() throws IOException {
    ReadOnlyGameModel localModel = new MockModelStrategyFour(grid, mockPlayer, mockOpponent);

    ChainStrategy chainStrategy = new ChainStrategy(grid);

    StrategyInterface strategyFour = new StrategyFour(localModel, new MockOpponentStrategy());
    StrategyInterface strategyThree = new StrategyThree(localModel);

    chainStrategy.addStrategy(strategyFour);
    chainStrategy.addStrategy(strategyThree);

    Moves bestMove = chainStrategy.getBestMove(mockPlayer);

    assertNotNull("Best move should not be null when using multiple strategies in the chain", bestMove);
    assertTrue("Selected cell should be empty",
            grid.getCell(bestMove.getRow(), bestMove.getCol()).isEmpty());
  }

  @Test
  public void testStrategyOrderMatters() throws IOException {
    ReadOnlyGameModel localModel = new MockModelStrategyFour(grid, mockPlayer, mockOpponent);

    ChainStrategy chainStrategy = new ChainStrategy(grid);

    StrategyInterface strategyThree = new StrategyThree(localModel);
    StrategyInterface strategyFour = new StrategyFour(localModel, new MockOpponentStrategy());
    chainStrategy.addStrategy(strategyThree);
    chainStrategy.addStrategy(strategyFour);

    Moves bestMove = chainStrategy.getBestMove(mockPlayer);

    assertNotNull(
            "Best move should not be null when"
            + "using multiple strategies in different order",
            bestMove);
    assertTrue("Selected cell should be empty",
            grid.getCell(bestMove.getRow(), bestMove.getCol()).isEmpty());
  }

  @Test
  public void testNoStrategiesInChain() {
    ChainStrategy chainStrategy = new ChainStrategy(grid);
    Moves bestMove = chainStrategy.getBestMove(mockPlayer);
    assertNull("Best move should be null when no strategies are in the chain", bestMove);
  }


  // Mock opponent strategy to simulate the opponent's behavior
  private static class MockOpponentStrategy implements StrategyInterface {
    @Override
    public Moves getBestMove(IPlayer player) {
      return new Moves(
              new Card("OpponentCard", 1, 1, 1, 1), 1, 1);
    }
  }




}
