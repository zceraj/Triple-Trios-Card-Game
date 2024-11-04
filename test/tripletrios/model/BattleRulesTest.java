package tripletrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import cs3500.tripletrios.model.BattleRules;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.PlayerColor;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * test for the battle rules class in the main.
 */
public class BattleRulesTest {

  private BattleRules battleRules;
  private GameModelImpl game;
  private IPlayer player1;
  private IPlayer player2;
  private Grid grid;

  /**
   * sets up everything needed to test.
   */
  @BeforeEach
  public void setUp() {
    // Initialize players
    player1 = new HumanPlayer("Player1", PlayerColor.RED);
    player2 = new HumanPlayer("Player2", PlayerColor.BLUE);

    game = new GameModelImpl(
            "." + File.separator + "TESTINGFILES" + File.separator + "battle_rules_grid",
            player1,
            player2);

    this.game.startGame(
            "." + File.separator + "TESTINGFILES" + File.separator + "full_card_set.txt");

    // Initialize game model and battle rules
    battleRules = new BattleRules(game);
    this.grid = game.getGameGrid();
  }

  @Test
  public void testBattleExecution_SingleBattle() {
    // Place initial cards on grid for battle
    Card card1 = player1.getHand().get(0);
    Card card2 = player2.getHand().get(0);

    game.placeCard(card1, 1, 1);
    game.updateOwner(1, 1, player1);

    game.placeCard(card2, 0, 1);
    game.updateOwner(0, 1, player2);

    // Initiate battle and verify the outcome
    battleRules.startBattle(grid, 1, 1, player1);

    // Check that player1 has won the cell (1,0)
    assertEquals(player2, game.getCellsPlayer(0, 1), "Player 2 should have won the battle.");
  }
}
