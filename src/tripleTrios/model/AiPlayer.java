package tripleTrios.model;

import java.util.List;

/**
 * Represents an AI player in the game.
 * Implements the IPlayer interface.
 * This class can be expanded to include AI logic for playing the game.
 */
public class AiPlayer implements IPlayer{

  /**
   * Gets the name of the player.
   * @return the name of the player
   */
  @Override
  public String getName() {
    return "";
  }

  /**
   * Gets the color of the player.
   * @return the color of the player
   */
  @Override
  public String getColor() {
    return "";
  }

  /**
   * Gets the list of cards currently held by the player.
   * @return a list of Card objects representing the player's hand
   */
  @Override
  public List<Card> getHand() {
    return List.of();
  }

  /**
   * Adds a card to the player's hand.
   * @param card to be added to the player's hand.
   */
  @Override
  public void addCardToHand(Card card) {

  }

  /**
   * Removes a card from the player's hand.
   * @param card to be removed from the player's hand.
   */
  @Override
  public void removeCardFromHand(Card card) {

  }

  /**
   * Sets the hand of the player with a specified list of cards.
   * @param playerHand a List of Cards to be set as the player's hand.
   */
  @Override
  public void setHand(List<Card> playerHand) {

  }

  /**
   * Places the card at the specified row and column.
   * @param card
   * @param row
   * @param col
   */
  @Override
  public void placeTheCard(Card card, int row, int col) {

  }
}
