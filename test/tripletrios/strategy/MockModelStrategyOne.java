package tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockModelStrategyOne implements ReadOnlyGameModel {

  private List<String> methodCalls;
  private Grid mockGrid;
  private Map<IPlayer, Integer> mockScores;
  private IPlayer currentPlayer;
  private IPlayer mockPlayer1;
  private IPlayer mockPlayer2;
  private IPlayer winner;
  private boolean gameOver;

  public MockModelStrategyOne(Grid mockGrid, IPlayer mockPlayer1, IPlayer mockPlayer2) {
    this.methodCalls = new ArrayList<>();
    this.mockGrid = mockGrid;
    this.mockScores = new HashMap<>();
    this.mockPlayer1 = mockPlayer1;
    this.mockPlayer2 = mockPlayer2;
    this.currentPlayer = mockPlayer1;
    this.winner = null;
    this.gameOver = false;
  }

  @Override
  public Grid getGameGrid() {
    methodCalls.add("getGameGrid");
    return new Grid(mockGrid);
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

    if (cell.isEmpty()) {
      return null;
    } else {
      if (cell.getCard() != null) {
        return mockPlayer1;
      } else {
        return mockPlayer2;
      }
    }
  }

  @Override
  public int getScore(IPlayer player) {
    methodCalls.add("getScore");
    return mockScores.getOrDefault(player, 0);
  }

  @Override
  public IPlayer getOtherPlayer() {
    methodCalls.add("getOtherPlayer");
    return currentPlayer == mockPlayer1 ? mockPlayer2 : mockPlayer1;
  }

  @Override
  public boolean isGameOver() {
    methodCalls.add("isGameOver");
    return gameOver;
  }

  @Override
  public IPlayer getWinner() {
    methodCalls.add("getWinner");
    return winner;
  }

  @Override
  public IPlayer getPlayerFromCard(CardInterface card) {
    methodCalls.add("getPlayerFromCard");
    // Assuming mockPlayer1 owns all cards in this mock setup
    return mockPlayer1;  // or provide logic based on your needs
  }

  /**
   * Sets the mock game state to be over.
   *
   * @param gameOver whether the game is over or not.
   */
  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  /**
   * Sets the mock winner of the game.
   *
   * @param winner the winner of the game.
   */
  public void setWinner(IPlayer winner) {
    this.winner = winner;
  }

  /**
   * Records method calls for verification purposes.
   *
   * @return A list of method calls made during the test.
   */
  public List<String> getMethodCalls() {
    return methodCalls;
  }
}
