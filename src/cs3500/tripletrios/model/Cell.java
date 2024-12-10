package cs3500.tripletrios.model;


/**
 * Represents each cell in the grid of the game. Cells can either be card cells (where cards can
 * be placed) or holes (where no cards can be placed).
 */
public class Cell implements CellInterface {
  private final boolean isCardCell;
  private CardInterface card;
  private final int row;
  private final int col;

  /**
   * Creates a cell at the given row and column, specifying if it's a hole or not.
   *
   * @param row        Row position of the cell.
   * @param col        Column position of the cell.
   * @param isCardCell Whether this cell is a card cell.
   */
  public Cell(int row, int col, boolean isCardCell) {
    this.isCardCell = isCardCell;
    this.card = null;
    this.row = row;
    this.col = col;
  }

  /**
   * Copy constructor for a new cell.
   *
   * @param original The cell to copy.
   */
  public Cell(CellInterface original) {
    this.row = original.getRow();
    this.col = original.getCol();
    this.isCardCell = original.isCardCell();
    this.card = original.getCard();
  }


  /**
   * Checks if the cell is a card cell.
   *
   * @return True if the cell is a card cell, false if it's a hole.
   */
  @Override
  public boolean isCardCell() {
    return isCardCell;
  }


  /**
   * Checks if the cell is empty.
   *
   * @return True if the cell is empty, false if it contains a card
   */
  @Override
  public boolean isEmpty() {
    return card == null;
  }


  /**
   * Places a card in this cell if it's a card cell and currently empty.
   *
   * @param card The card to place in this cell.
   */
  @Override
  public void setCard(CardInterface card) {
    if (card == null) {
      throw new IllegalArgumentException("Cannot place a null card in a cell.");
    }
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
   *
   * @return The card in this cell, or null if the cell is empty.
   */
  @Override
  public CardInterface getCard() {
    return card;
  }

  /**
   * Gets the row of a Cell.
   *
   * @return row in which the cell is in
   */
  @Override
  public int getRow() {
    return row;
  }

  /**
   * Gets the column of a Cell.
   *
   * @return column in which the cell is in
   */
  @Override
  public int getCol() {
    return col;
  }

}
