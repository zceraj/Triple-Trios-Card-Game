package tripleTrios.model;
import java.util.List;


public class Player {
  private String name;
  private PlayerColor color; // Red or Blue
  private List<Card> hand;

  // constructor

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
