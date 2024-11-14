package tripletrios.strategy;

import cs3500.tripletrios.controller.GridFileReader;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.strategy.Moves;
import cs3500.tripletrios.strategy.StrategyFour;
import cs3500.tripletrios.strategy.StrategyInterface;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StrategyFourTest {
  private MockModelStrategyFour model;
  private IPlayer mockPlayer;
  private StrategyFour strategy;
  IPlayer mockOpponent = new MockPlayer("Player 2");


  @Before
  public void setUp() throws IOException {
    GridFileReader gridFileReader = new GridFileReader("TESTINGFILES/grid3x3.txt");
    Grid grid = new Grid(gridFileReader.getGrid());

    mockPlayer = new MockPlayer("Player 1");

    List<CardInterface> hand = new ArrayList<>();
    hand.add(new Card("Card1", 5, 3, 2, 1));  // Card with specific attack values in four directions
    hand.add(new Card("Card2", 7, 4, 6, 3));  // Another card with attack values in four directions
    mockPlayer.setHand(hand);

    // Initialize the model and strategy
    model = new MockModelStrategyFour(grid, mockPlayer, mockOpponent);
    strategy = new StrategyFour(model, new MockOpponentStrategy());
  }

  @Test
  public void testGetBestMove() {
    Moves bestMove = strategy.getBestMove(mockPlayer);

    assertNotNull("Best move should not be null", bestMove);
    System.out.println("Best move found: Row - " + bestMove.getRow() + ", Column - " + bestMove.getCol());
    assertTrue("Selected cell should be empty",
            model.getGameGrid().getCell(bestMove.getRow(), bestMove.getCol()).isEmpty());
  }

  @Test
  public void testNoCardsInHand() {
    mockPlayer.setHand(new ArrayList<>());

    Moves bestMove = strategy.getBestMove(mockPlayer);

    assertNull("Best move should be null when player has no cards", bestMove);
  }


  @Test
  public void testOpponentBlockingMove() {
    Moves bestMove = strategy.getBestMove(mockPlayer);

    assertNotNull("Best move should not be null", bestMove);

    assertTrue("Selected cell should be empty",
            model.getGameGrid().getCell(bestMove.getRow(), bestMove.getCol()).isEmpty());

    model.getGameGrid().getCell(bestMove.getRow(), bestMove.getCol()).setCard(bestMove.getCard());

    MockOpponentStrategy opponentStrategy = new MockOpponentStrategy();
    Moves opponentBestMoveAfterPlayer = opponentStrategy.getBestMove(mockOpponent);


    assertFalse("Opponent's next best move should be less advantageous", opponentBestMoveAfterPlayer.getRow() == bestMove.getRow() && opponentBestMoveAfterPlayer.getCol() == bestMove.getCol());
  }


  // A mock opponent strategy to simulate opponent's behavior
  private static class MockOpponentStrategy implements StrategyInterface {
    @Override
    public Moves getBestMove(IPlayer opponent) {
      return new Moves(new Card("MockCard", 1, 1, 1, 1), 0, 1);
    }
  }
}
