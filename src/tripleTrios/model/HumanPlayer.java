package tripleTrios.model;
import java.util.ArrayList;
import java.util.List;


public class HumanPlayer implements IPlayer {
  private final String name;
  private final PlayerColor color;
  private final List<Card> hand;

  HumanPlayer(String name, PlayerColor color) {
    this.hand = new ArrayList<>();
    this.name = name;
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public String getColor() {
    return color.toString();
  }

  public void addCardToHand(Card card) {
    hand.add(card);
  }

  public void removeCardFromHand(Card card) {
    hand.remove(card);
  }

  @Override
  public void setHand(List<Card> playerHand) {
    if (playerHand == null) {
      throw new IllegalArgumentException("Hand cannot be null.");
    }
    this.hand.clear();
    this.hand.addAll(playerHand);
  }

  public List<Card> getHand() {
    return hand;
  }

  public Card playCard(int index) {
    return hand.remove(index);
  }

  @Override
  public String toString() {
    return name + " (" + color + ") - Hand: " + hand;
  }
}
