package cs3500.tripletrios.strategy;

import cs3500.tripletrios.controller.GridFileReader;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

import tripletrios.strategy.MockModelStrategyOne;
import tripletrios.strategy.MockPlayer;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

  /**
   * Test class for the simplest strategy, StrategyOne, using a mock model to create a transcript.
   */
  public class SimplestStrategyTest {
    private MockModelStrategyOne model;
    private IPlayer player;
    private StrategyOne strategy;

    @Before
    public void setUp() throws IOException {
      // Load the grid from the 3x3NoHoles file
      GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/3x3NoHoles");
      Grid grid = new Grid(gridFileReader.getGrid());

      // Initialize the mock player
      player = new MockPlayer("Player Red");

      // Add some cards to the player's hand for the test
      List<CardInterface> hand = new ArrayList<>();
      hand.add(new Card("Card1", 3, 2, 1, 5));
      hand.add(new Card("Card2", 6, 4, 3, 2));
      player.setHand(hand);

      // Initialize the model and strategy
      model = new MockModelStrategyOne(grid, player, new MockPlayer("Player Blue"));
      strategy = new StrategyOne((ReadOnlyGameModel) model);
    }

    @Test
    public void testGetBestMove() {
      // Execute the strategy to determine the best move
      Moves bestMove = strategy.getBestMove(player);

      // Validate that the best move is not null
      assertNotNull("Best move should not be null", bestMove);
      System.out.println("Best move found by StrategyOne: Row - " + bestMove.getRow() + ", Column - " + bestMove.getCol());
    }
  }




