package tripletrios.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


import java.util.List;

import cs3500.tripletrios.model.BattleRules;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
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
  private final boolean[][] grid = {
          {false, true, true},
          {false, true, false},
          {true, true, false}
  };
  private Grid gridAsGrid = new Grid(grid);

  /**
   * sets up everything needed to test.
   */
  @BeforeEach
  public void setUp() {
    // Initialize players
    player1 = new HumanPlayer("Player1", PlayerColor.RED);
    player2 = new HumanPlayer("Player2", PlayerColor.BLUE);
    List<CardInterface> cards = List.of(
            new Card("Tiger", 4, 2, 5, 3),
            new Card("Elephant", 5, 1, 4, 2),
            new Card("Giraffe", 2, 4, 3, 5),
            new Card("Zebra", 1, 3, 5, 4),
            new Card("Panda", 4, 4, 4, 4),
            new Card("Wolf", 3, 2, 1, 5)
    );

    game = new GameModelImpl(
            grid,
            player1,
            player2);

    this.game.startGame(cards);

    // Initialize game model and battle rules
    battleRules = new BattleRules(game);
    this.gridAsGrid = game.getGameGrid();
  }

  @Test
  public void testBattleExecution_SingleBattle() {
    CardInterface card1 = player1.getHand().get(0);
    CardInterface card2 = player2.getHand().get(0);

    game.placeCard(card1, 1, 1);
    game.updateOwner(1, 1, player1);

    game.placeCard(card2, 0, 1);
    game.updateOwner(0, 1, player2);
    battleRules.startBattle(gridAsGrid, 1, 1, player1);

    assertEquals(player2, game.getCellsPlayer(0, 1), "Player 2 should have won the battle.");
  }
}
