package tripletrios.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.IPlayer;

/**
 * MockPlayer class to be used for testing purposes.
 * Implements the IPlayer interface.
 */
public class MockPlayer implements IPlayer {

  private final String name;
  private List<CardInterface> hand;

  /**
   * Constructs a MockPlayer with the given name.
   *
   * @param name The name of the player.
   */
  public MockPlayer(String name) {
    this.name = name;
    this.hand = new ArrayList<>();
  }

  @Override
  public void addCardToHand(CardInterface card) {
    hand.add(card);
    System.out.println("Added card to " + name + "'s hand: " + card.toString());
  }

  @Override
  public void removeCardFromHand(CardInterface card) {
    hand.remove(card);
    System.out.println("Removed card from " + name + "'s hand: " + card.toString());
  }

  @Override
  public void setHand(List<CardInterface> playerHand) {
    this.hand = new ArrayList<>(playerHand); // Assign a copy of the list to avoid modifications from outside.
    System.out.println("Set hand for " + name + ": " + hand);
  }

  @Override
  public void placeTheCard(CardInterface card, int row, int col) {
    System.out.println("MockPlayer " + name + " placed card " + card + " at (" + row + ", " + col + ")");
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getColor() {
    return "mock-color"; // Assuming color is needed, can be customized as required
  }

  /**
   * Returns a string representation of the MockPlayer.
   *
   * @return A string with the player's name and hand details.
   */
  @Override
  public String toString() {
    String sb = "MockPlayer{name='" + name + "', " +
            "hand=" + hand + "}";
    return sb;
  }

  /**
   * Gets the current player's hand of cards.
   *
   * @return The hand of the player.
   */
  public List<CardInterface> getHand() {
    return new ArrayList<>(hand);
  }

  /**
   * Clear the hand of the player.
   */
  public void clearHand() {
    hand.clear();
  }

}