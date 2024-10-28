package tripleTrios.model;

/**
 * Represents the interface fo the model for the Triple Trios card game.
 */
public interface GameModel {

  /**
   * Places the given card at the specified row and column in the game grid.
   * @param card The card to place
   * @param row The row to place the card
   * @param col The column to place the card
   */
  void placeCard(Card card, int row, int col);

  /**
   * Checks if the game is over.
   * @return True if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Gets the winner of the game.
   * @return The winner of the game
   */
  Player getWinner();

  /**
   * Gets the game grid.
   * @return The game grid
   */
  Grid getGameGrid();

  /**
   * Gets the current player.
   * @return The current player
   */
  Player getCurPlayer();

  /**
   * Advances the game to the next turn.
   */
  void nextTurn();

  /**
   * Resolves battles at the given row and column.
   * @param row The row of the battle
   * @param col The column of the battle
   */
  void battles(int row, int col);

}
