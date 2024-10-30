package tripleTrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a human player in the card game, holding a hand of cards
 * and providing methods to manage the player's hand.
 *
 * <p>A HumanPlayer object has a name, a color representing the player's identity
 * in the game, and a list of cards in hand. This class implements the
 * {@link IPlayer} interface and provides methods for adding, removing, and
 * playing cards from the player's hand.</p>
 */
public class HumanPlayer implements IPlayer {
  private final String name;
  private final PlayerColor color;
  private final List<Card> hand;

  /**
   * Constructs a new HumanPlayer with the specified name and color.
   *
   * @param name  the name of the player
   * @param color the color representing the player's identity
   */
  HumanPlayer(String name, PlayerColor color) {
    this.hand = new ArrayList<>();
    this.name = name;
    this.color = color;
  }

  /**
   * Gets the name of the player.
   *
   * @return the name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the color of the player as a string.
   *
   * @return the color of the player
   */
  public String getColor() {
    return color.toString();
  }

  /**
   * Adds a card to the player's hand.
   *
   * @param card the card to be added to the hand
   */
  public void addCardToHand(Card card) {
    hand.add(card);
  }

  /**
   * Removes a specified card from the player's hand.
   *
   * @param card the card to be removed from the hand
   */
  public void removeCardFromHand(Card card) {
    hand.remove(card);
  }

  /**
   * Sets the player's hand to the specified list of cards.
   *
   * @param playerHand the list of cards to set as the player's hand
   * @throws IllegalArgumentException if playerHand is null
   */
  @Override
  public void setHand(List<Card> playerHand) {
    if (playerHand == null) {
      throw new IllegalArgumentException("Hand cannot be null.");
    }
    this.hand.clear();
    this.hand.addAll(playerHand);
  }



  /**
   * Gets the list of cards currently in the player's hand.
   *
   * @return the list of cards in the player's hand
   */
  public List<Card> getHand() {
    return hand;
  }

  /**
   * Plays a card from the player's hand at the specified index.
   *
   * @param index the index of the card to be played
   * @return the card that was played
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  public Card playCard(int index) {

    return hand.remove(index);
  }

  public void placeTheCard(Card card, int row, int col) {
    //place the card at the specified row and column
  }


  /**
   * Returns a string representation of the HumanPlayer, including the player's
   * name, color, and current hand.
   *
   * @return a string representation of the HumanPlayer
   */
  @Override
  public String toString() {
    return name + " (" + color + ") - Hand: " + hand;
  }
}
