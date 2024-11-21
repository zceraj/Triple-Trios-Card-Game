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
import cs3500.tripletrios.strategy.StrategyInterface;
import cs3500.tripletrios.strategy.StrategyOne;

import static org.junit.Assert.assertNotNull;

/**
 * Test class for the simplest strategy, StrategyOne, using a mock model.
 */
public class StrategyTranscriptTest {

  private AiPlayer aiPlayer;

  @Before
  public void setUp() throws IOException {
    // Initialize the grid from the file representing a 3x3 grid with no holes.
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid3x3.txt");
    Grid grid = new Grid(gridFileReader.getGrid());

    // Initialize opponent and AI player
    IPlayer opponent = new HumanPlayer("Opponent", PlayerColor.RED);

    // Initialize cards
    CardInterface card1 = new Card("Card1", 4, 6, 2, 3);
    CardInterface card2 = new Card("Card2", 3, 7, 1, 5);
    CardInterface card3 = new Card("Card3", 5, 4, 3, 6);
    List<CardInterface> hand = new ArrayList<>();
    hand.add(card1);
    hand.add(card2);
    hand.add(card3);

    // Initialize the mock model for testing purposes
    MockModelAIPlayer mockModel = new MockModelAIPlayer(grid, opponent, null);

    // Initialize the strategy and AI player
    StrategyInterface strategy = new StrategyOne(mockModel);
    aiPlayer = new AiPlayer("AI Player", PlayerColor.RED);
    aiPlayer.setHand(hand);
    aiPlayer.setStrategy(strategy);
  }

  @Test
  public void testGenerateTranscriptForSimpleStrategy() {
    // Determine the best move using the assigned strategy
    Moves move = aiPlayer.determineNextMove();

    // Validate that the move is not null
    assertNotNull("AI should determine a valid move.", move);

    // Print out the move details for the transcript
    System.out.println("Strategy Transcript:");
    System.out.println("Player: " + aiPlayer.getName() + " (" + aiPlayer.getColor() + ")");
    System.out.println("Chosen Card: " + move.getCard().getCardName());
    System.out.println("Chosen Position: Row " + move.getRow() + ", Column " + move.getCol());

    // Execute the move by placing the card
    aiPlayer.placeTheCard(move.getCard(), move.getRow(), move.getCol());
  }

}
