package tripletrios.model;

/**
 * Represents each cell in the grid of the game. Cells can either be card cells (where cards can
 * be placed) or holes (where no cards can be placed).
 */
public class Cell {
  private final boolean isCardCell;
  private Card card;

  /**
   * Creates a cell at the given row and column, specifying if it's a hole or not.
   * @param row Row position of the cell.
   * @param col Column position of the cell.
   * @param isCardCell Whether this cell is a card cell.
   */
  public Cell(int row, int col, boolean isCardCell) {
    this.isCardCell = isCardCell;
    this.card = null; // Initialize as empty
  }


  /**
   * Checks if the cell is a card cell.
   * @return True if the cell is a card cell, false if it's a hole.
   */
  public boolean isCardCell() {
    return isCardCell;
  }


  /**
   * Checks if the cell is empty.
   * @return True if the cell is empty, false if it contains a card
   */
  public boolean isEmpty() {
    return card == null;
  }


  /**
   * Places a card in this cell if it's a card cell and currently empty.
   * @param card The card to place in this cell.
   */
  public void setCard(Card card) {
    if (!isCardCell) {
      throw new IllegalArgumentException("Cannot place a card in a hole.");
    }
    if (!isEmpty()) {
      throw new IllegalStateException("Cell already occupied by a card.");
    }
    this.card = card;
  }


  /**
   * Gets the card in this cell.
   * @return The card in this cell, or null if the cell is empty.
   */
  public Card getCard() {
    return card;
  }



}
