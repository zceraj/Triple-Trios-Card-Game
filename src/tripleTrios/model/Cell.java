package tripleTrios.model;

/**
 * Represents each cell in the grid of the game.
 */
public class Cell {
  private boolean isHole;
  private Card card;

  boolean isCardCell() {
    return false;
  }

  boolean isHole() {
    return isHole;
  }

  Card getCard() {
    return null;
  }

  void placeCard(Card card, Player player) {
  }

  // to string


}
