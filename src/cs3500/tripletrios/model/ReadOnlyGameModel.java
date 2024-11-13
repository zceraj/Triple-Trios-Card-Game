package cs3500.tripletrios.model;

/**
 * Represents a read-only version of the Triple Trios game model.
 */
public interface ReadOnlyGameModel {

  /**
   * Checks if the game is over.
   * @return True if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Gets the winner of the game.
   * @return The winner of the game
   */
  IPlayer getWinner();

  /**
   * Gets the game grid.
   * @return a copy of the game grid
   */
  Grid getGameGrid();

  /**
   * Gets the current player.
   * @return a copy of the current player
   */
  IPlayer getCurPlayer();

  /**
   * Gets the player that owns the card in the cell at the specified row and column.
   *
   * @param row The row of the cell
   * @param col The column of the cell
   * @return The copy of the player that owns the card in the cell
   */
  IPlayer getCellsPlayer(int row, int col);

  /**
   * Gets the score of the given player.
   * @param player The player to get the score of
   * @return The score of the player
   */
  int getScore(IPlayer player);

  IPlayer getPlayerFromCard(CardInterface card);

  IPlayer getOtherPlayer();
}
