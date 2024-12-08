package tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

import java.io.IOException;

/**
 * The MockModelStrategyFour class implements the ReadOnlyGameModel interface
 * and represents a mock implementation of a game strategy. This class stores
 * the current state of the game, including the grid, current player, and
 * opponent player. It is designed to be used as part of a strategy pattern
 * for a game model.
 */
public class MockModelStrategyFour implements ReadOnlyGameModel {
  private final Grid grid;
  private final IPlayer currentPlayer;
  private final IPlayer opponentPlayer;

  /**
   * Constructs a new MockModelStrategyFour instance with the specified
   * grid, current player, and opponent player. The grid is deep-copied to
   * ensure that the original grid object is not modified by this strategy.
   */
  public MockModelStrategyFour(
          Grid grid, IPlayer currentPlayer, IPlayer opponentPlayer) throws IOException {
    this.grid = new Grid(grid);
    this.currentPlayer = currentPlayer;
    this.opponentPlayer = opponentPlayer;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public IPlayer getWinner() {
    return null;
  }

  @Override
  public Grid getGameGrid() {
    return grid;
  }

  @Override
  public IPlayer getCurPlayer() {
    return currentPlayer;
  }

  @Override
  public IPlayer getCellsPlayer(int row, int col) {
    Cell cell = grid.getCell(row, col);
    return cell.isEmpty() ? null : currentPlayer;
  }

  @Override
  public int getScore(IPlayer player) {
    return player == currentPlayer ? 10 : 5;
  }

  @Override
  public IPlayer getPlayerFromCard(CardInterface card) {
    return currentPlayer;
  }

  @Override
  public IPlayer getOtherPlayer() {
    return opponentPlayer;
  }

  @Override
  public boolean isGameStarted() {
    return false;
  }

}
