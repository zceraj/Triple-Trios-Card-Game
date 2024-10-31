package tripleTrios.model;

import java.util.List;

/**
 * Represents the interface fo the model for the Triple Trios card game.
 */
public interface GameModel {

  /**
   * Starts the game with the given options. The deck given is used
   * to deal out the cards to each player . Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param player1 The first player
   * @param gridFilePath is the filepath to the grid
   * @param cardFilePath is the filepath to the cards
   * @param player2 The second player
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if numPalettes < 2 or handSize <= 0
   * @throws IllegalArgumentException if deck's size is not large enough to set up the game
   * @throws IllegalArgumentException if deck has non-unique cards or null cards
   */
  void startGame(String gridFilePath, String cardFilePath, IPlayer player1, IPlayer player2);

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
  IPlayer getWinner();

  /**
   * Gets the game grid.
   * @return The game grid
   */
  Grid getGameGrid();

  /**
   * Gets the current player.
   * @return The current player
   */
  IPlayer getCurPlayer();

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

  /**
   * Gets the total number of card cells in the grid.
   * @param grid The grid to check
   * @return The total number of card cells in the grid
   */
  int getTotalCardCells(Grid grid);

}
