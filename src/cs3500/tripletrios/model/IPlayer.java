package cs3500.tripletrios.model;

import java.util.List;

import cs3500.tripletrios.strategy.Moves;

/**
 * Interface representing a player in the Triple Trios game.
 * This interface defines the essential actions and properties of a player.
 */
public interface IPlayer {

  /**
   * Retrieves the name of the player.
   *
   * @return the name of the player as a {@code String}.
   */
  String getName();

  /**
   * Retrieves the color associated with the player.
   *
   * @return the color of the player as a {@code String}.
   */
  String getColor();

  /**
   * Retrieves the list of cards currently held by the player.
   *
   * @return a list of {@code Card} objects representing the player's hand.
   */
  List<CardInterface> getHand();

  /**
   * Adds a card to the player's hand.
   *
   * @param card to be added to the player's hand.
   */
  void addCardToHand(CardInterface card);

  /**
   * Removes a card from the player's hand.
   *
   * @param card to be removed from the player's hand.
   */
  void removeCardFromHand(CardInterface card);

  /**
   * Sets the hand of the player with a specified list of cards.
   *
   * @param playerHand a List of Cards to be set as the player's hand.
   */
  void setHand(List<CardInterface> playerHand);

  /**
   * Places a card on the grid at the specified row and column.
   * @param card the card to be placed
   * @param row the row to place the card
   */
  void placeTheCard(CardInterface card, int row, int col);

  /**
   * determines the next move for the player.
   * Used Specifically for AI players.
   * @return the move to be made
   */
  Moves determineNextMove(Grid grid);
}
