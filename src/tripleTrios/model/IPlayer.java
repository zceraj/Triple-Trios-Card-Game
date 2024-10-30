package tripleTrios.model;

import java.util.List;

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
   * @return a of {@code Card} objects representing the player's hand.
   */
  List<Card> getHand();

  /**
   * Adds a card to the player's hand.
   *
   * @param card to be added to the player's hand.
   */
  void addCardToHand(Card card);

  /**
   * Removes a card from the player's hand.
   *
   * @param card to be removed from the player's hand.
   */
  void removeCardFromHand(Card card);

  /**
   * Sets the hand of the player with a specified list of cards.
   *
   * @param playerHand a List of Cards to be set as the player's hand.
   */
  void setHand(List<Card> playerHand);
  
  void placeTheCard(Card card, int row, int col);
}
