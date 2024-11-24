package cs3500.tripletrios.model;

import java.util.List;

import cs3500.tripletrios.observing.ObservableInterface;

/**
 * Represents the interface fo the model for the Triple Trios card game.
 */
public interface GameModel extends ReadOnlyGameModel, ObservableInterface {

  /**
   * Starts the game with the given options. The deck given is used
   * to deal out the cards to each player . Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param cardIn is the list of cards generated from the filepath.
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if numPalettes < 2 or handSize <= 0
   * @throws IllegalArgumentException if deck's size is not large enough to set up the game
   * @throws IllegalArgumentException if deck has non-unique cards or null cards
   */
  void startGame(List<CardInterface> cardIn);

  /**
   * Places the given card at the specified row and column in the game grid.
   *
   * @param card The card to place
   * @param row  The row to place the card
   * @param col  The column to place the card
   */
  void placeCard(CardInterface card, int row, int col);

  /**
   * Advances the game to the next turn.
   */
  void nextTurn();

  /**
   * Resolves battles at the given row and column.
   *
   * @param row The row of the battle
   * @param col The column of the battle
   */
  void battles(int row, int col);

  void updateOwner(int row, int col, IPlayer player);
}
