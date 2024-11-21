package tripletrios.model;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

import java.util.HashMap;
import java.util.Map;

/**
 * A mock implementation of ReadOnlyGameModel used for testing purposes.
 */
public class MockModelAIPlayer implements ReadOnlyGameModel {

  private final Grid grid;
  private final IPlayer currentPlayer;
  private final IPlayer otherPlayer;
  private final Map<CardInterface, IPlayer> cardOwnership;
  private final Map<String, Integer> playerScores;

  /**
   * Constructs a mock model with the specified grid, current player, and other player.
   * @param grid The grid to use in the mock model
   * @param currentPlayer The current player
   * @param otherPlayer The other player
   */
  public MockModelAIPlayer(Grid grid, IPlayer currentPlayer, IPlayer otherPlayer) {
    this.grid = grid;
    this.currentPlayer = currentPlayer;
    this.otherPlayer = otherPlayer;
    this.cardOwnership = new HashMap<>();
    this.playerScores = new HashMap<>();

    // Initialize player scores if players are provided.
    if (currentPlayer != null) {
      playerScores.put(currentPlayer.getName(), 5); // Example initial score
    }
    if (otherPlayer != null) {
      playerScores.put(otherPlayer.getName(), 5); // Example initial score
    }
  }

  @Override
  public boolean isGameOver() {
    return getTotalEmptyCells() == 0;
  }

  @Override
  public IPlayer getWinner() {
    if (!isGameOver()) {
      return null;
    }

    int currentPlayerScore = getScore(currentPlayer);
    int otherPlayerScore = getScore(otherPlayer);

    if (currentPlayerScore > otherPlayerScore) {
      return currentPlayer;
    } else if (currentPlayerScore < otherPlayerScore) {
      return otherPlayer;
    }
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
    CardInterface card = grid.getCell(row, col).getCard();
    return card != null ? cardOwnership.get(card) : null;
  }

  @Override
  public IPlayer getOtherPlayer() {
    return otherPlayer;
  }

  @Override
  public int getScore(IPlayer player) {
    if (player == null) {
      return 0;
    }
    return playerScores.getOrDefault(player.getName(), 0);
  }

  @Override
  public IPlayer getPlayerFromCard(CardInterface card) {
    return cardOwnership.get(card);
  }

  /**
   * Place a card on the grid for testing purposes.
   *
   * @param card   The card to place.
   * @param row    The row to place the card.
   * @param col    The column to place the card.
   * @param player The player placing the card.
   */
  public void placeCardForTesting(CardInterface card, int row, int col, IPlayer player) {
    if (grid.isValidCell(row, col) && grid.getCell(row, col).isEmpty()) {
      grid.getCell(row, col).setCard(card);
      cardOwnership.put(card, player);
    } else {
      throw new IllegalArgumentException("Invalid cell coordinates or cell already occupied.");
    }
  }

  /**
   * Updates the player's score in the mock.
   */
  public void setPlayerScore(IPlayer player, int score) {
    if (player != null) {
      playerScores.put(player.getName(), score);
    }
  }

  /**
   * Resets the player score to the initial value.
   * This is useful when testing different game scenarios.
   */
  public void resetPlayerScore(IPlayer player) {
    if (player != null) {
      playerScores.put(player.getName(), 0);
    }
  }

  private int getTotalEmptyCells() {
    int emptyCells = 0;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (grid.getCell(row, col).isEmpty()) {
          emptyCells++;
        }
      }
    }
    return emptyCells;
  }


}
