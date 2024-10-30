package tripleTrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tripleTrios.model.IPlayer;

import static org.junit.Assert.assertFalse;

public class GameModelImplTest {
  private GameModelImpl game;


  @BeforeEach
  public void setUp() {
    IPlayer player1 = new HumanPlayer("Player1", PlayerColor.BLUE);
    IPlayer player2 = new HumanPlayer("Player2", PlayerColor.RED);

    boolean[][] cells = {
            {true, false, true},
            {false, true, true},
            {false, false, false}
    };
    Grid grid = new Grid(cells);
    GameModelImpl game = new GameModelImpl(grid, player1, player2);
  }

  @Test
  public void testIsGameOver() {
    assertFalse(game.isGameOver());
  }

  @Test
  public void testGetCount

}
