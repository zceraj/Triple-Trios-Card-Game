package tripletrios.strategy;

import cs3500.tripletrios.controller.GridFileReader;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.Direction;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;
import java.io.IOException;

public class MockModelStrategyFour implements ReadOnlyGameModel {
  private final Grid grid;
  private final IPlayer currentPlayer;
  private final IPlayer opponentPlayer;

  public MockModelStrategyFour(Grid grid, IPlayer currentPlayer, IPlayer opponentPlayer) throws IOException {
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
    return cell.isEmpty() ? null : currentPlayer; // Assume current player owns all placed cards for simplicity
  }

  @Override
  public int getScore(IPlayer player) {
    // Return a mock score based on some simple logic
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

}
