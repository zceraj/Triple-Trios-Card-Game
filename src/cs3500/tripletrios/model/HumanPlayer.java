package cs3500.tripletrios.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.strategy.Moves;
import cs3500.tripletrios.strategy.StrategyInterface;

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
  private final List<CardInterface> hand;

  /**
   * Constructs a new HumanPlayer with the specified name and color.
   *
   * @param name  the name of the player
   * @param color the color representing the player's identity
   */
  public HumanPlayer(String name, PlayerColor color) {
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
  public void addCardToHand(CardInterface card) {
    hand.add(card);
  }

  /**
   * Removes a specified card from the player's hand.
   *
   * @param card the card to be removed from the hand
   */
  public void removeCardFromHand(CardInterface card) {
    hand.remove(card);
  }

  /**
   * Sets the player's hand to the specified list of cards.
   *
   * @param playerHand the list of cards to set as the player's hand
   * @throws IllegalArgumentException if playerHand is null
   */
  @Override
  public void setHand(List<CardInterface> playerHand) {
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
  public List<CardInterface> getHand() {
    return new ArrayList<>(hand);
  }

  /**
   * Places a card on the game board at the specified row and column.
   *
   * @param card the card to place
   * @param row  the row to place the card
   * @param col  the column to place the card
   */
  public void placeTheCard(CardInterface card, int row, int col) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    }
    card.addRow(row);
    card.addCol(col);
    hand.remove(card);
  }

  /**
   * Determines the next move for the player.
   * Used specifically for AI players.
   *
   * @return the move to be made
   */
  public Moves determineNextMove() {
    throw new UnsupportedOperationException("Human players do not have a strategy.");
  }

  /**
   * Sets a strategy for an AiPlayer.
   * @param strategy the strategy to be set
   */
  public void setStrategy(StrategyInterface strategy) {
    throw new UnsupportedOperationException("Human players do not have a strategy.");
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
