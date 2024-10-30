package tripleTrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleRulesTest {

  private BattleRules battleRules;
  private GameModelImpl game;
  private Grid grid;
  private IPlayer player1;
  private IPlayer player2;

  @BeforeEach
  public void setUp() {
    // Initialize players
    player1 = new HumanPlayer("Player1", PlayerColor.RED);
    player2 = new HumanPlayer("Player2", PlayerColor.BLUE);

    // Set up a 3x3 grid with all cells available for cards
    boolean[][] cells = {
            {true, true, true},
            {true, true, true},
            {true, true, true}
    };
    grid = new Grid(cells);

    // Determine the required hand size based on grid size or game rules
    int requiredHandSize = (game.getTotalCardCells(grid) + 1) / 2;

    // Populate each player's hand with the required number of cards
    for (int i = 1; i <= requiredHandSize; i++) {
      Card card1 = new Card("Red Card " + i, 5, 2, 3, 4);
      Card card2 = new Card("Blue Card " + i, 2, 6, 5, 1);
      player1.addCardToHand(card1);
      player2.addCardToHand(card2);
    }

    // Initialize game model and battle rules
    game = new GameModelImpl(grid, player1, player2);
    battleRules = new BattleRules(game);
  }

  @Test
  public void testBattleExecution_SingleBattle() {
    // Place initial cards on grid for battle
    Card card1 = player1.getHand().get(0);
    Card card2 = player2.getHand().get(0);

    game.placeCard(card1, 1, 1);
    game.updateOwner(1, 1, player1);

    game.placeCard(card2, 1, 0);
    game.updateOwner(1, 0, player2);

    // Initiate battle and verify the outcome
    battleRules.startBattle(grid, 1, 1, player1);

    // Check that player1 has won the cell (1,0)
    assertEquals(player1, game.getCellsPlayer(1, 0), "Player 1 should have won the battle.");
  }
}
