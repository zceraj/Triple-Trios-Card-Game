package tripleTrios.model;

import java.util.List;

/**
 * Represents an AI player in the game.
 * Implements the IPlayer interface.
 */
public class AiPlayer implements IPlayer{

  /**
   * Returns the name of the AI player.
   *
   * @return the name "AI".
   */
  @Override
  public String getName() {
    //will return the name of the player
    return "AI";
  }

  /**
   * Returns the color of the AI player, which should be the opposite of the human player's color.
   *
   * @return the color of the AI player as a string.
   */
  @Override
  public String getColor() {
    //will be the opposite color of the human player
    return "";
  }

  /**
   * Returns the current hand of the AI player as a list of cards.
   *
   * @return a list of cards representing the AI player's hand.
   */
  @Override
  public List<Card> getHand() {
    //will return the hand of the AI player
    return List.of();
  }

  /**
   * Adds the specified card to the AI player's hand.
   *
   * @param card the card to be added to the hand.
   */
  @Override
  public void addCardToHand(Card card) {
    //adds the cards to AI player hand
  }

  /**
   * Removes the specified card from the AI player's hand.
   *
   * @param card the card to be removed from the hand.
   */
  @Override
  public void removeCardFromHand(Card card) {
    //removes played cards from AI player hand
  }

  /**
   * Sets the AI player's hand to the specified list of cards.
   *
   * @param playerHand a list of cards to set as the AI player's hand.
   */
  @Override
  public void setHand(List<Card> playerHand) {
    //sets the hand of the AI player
  }
}
