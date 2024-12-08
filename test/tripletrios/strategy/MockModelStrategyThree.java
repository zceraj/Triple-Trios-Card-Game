package tripletrios.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

/**
 * A mock model class for StrategyThree for testing purposes.
 */
public class MockModelStrategyThree implements ReadOnlyGameModel {

  private final Grid mockGrid;
  private final IPlayer currentPlayer;
  private final IPlayer opponentPlayer;
  private final List<String> methodCalls;

  /**
   * Constructs a MockModelStrategyThree.
   *
   * @param grid           the grid used in the game
   * @param currentPlayer  the current player
   * @param opponentPlayer the opponent player
   */
  public MockModelStrategyThree(Grid grid, IPlayer currentPlayer, IPlayer opponentPlayer) {
    this.mockGrid = grid;
    this.currentPlayer = currentPlayer;
    this.opponentPlayer = opponentPlayer;
    this.methodCalls = new ArrayList<>();
  }

  @Override
  public boolean isGameOver() {
    methodCalls.add("isGameOver");
    return false;
  }

  @Override
  public IPlayer getWinner() {
    methodCalls.add("getWinner");
    return null;
  }

  @Override
  public Grid getGameGrid() {
    methodCalls.add("getGameGrid");
    return mockGrid;
  }

  @Override
  public IPlayer getCurPlayer() {
    methodCalls.add("getCurPlayer");
    return currentPlayer;
  }

  @Override
  public IPlayer getCellsPlayer(int row, int col) {
    methodCalls.add("getCellsPlayer");
    Cell cell = mockGrid.getCell(row, col);
    return cell.isEmpty() ? null : opponentPlayer;
  }

  @Override
  public int getScore(IPlayer player) {
    methodCalls.add("getScore");
    return 0;
  }

  @Override
  public IPlayer getPlayerFromCard(CardInterface card) {
    methodCalls.add("getPlayerFromCard");
    return null;
  }

  @Override
  public IPlayer getOtherPlayer() {
    methodCalls.add("getOtherPlayer");
    return opponentPlayer;
  }

  @Override
  public boolean isGameStarted() {
    return false;
  }

  public List<String> getMethodCalls() {
    return methodCalls;
  }
}
