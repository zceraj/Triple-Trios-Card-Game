package tripleTrios.model;

/**
 * Represents each cell in the grid of the game.
 */
public class Cell {
  private boolean isHole;
  private Card card;
  private Cell type;
  private Cell cardCell;
  //should have if it is a hole
  //should have if it is a cardCell
  //should have if it is empty or not

  // constructor
  public Cell(Cell type) {
    this.type = type;
  }


  public boolean isCardCell() {
    return type == cardCell;
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
