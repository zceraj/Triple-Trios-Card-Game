package cs3500.tripletrios.model;

/**
 * Interface representing a cell in the Triple Trios game.
 */
public interface CellInterface {

  /**
   * Checks if the cell is a card cell.
   *
   * @return True if the cell is a card cell, false if it's a hole.
   */
  public boolean isCardCell();


  /**
   * Checks if the cell is empty.
   *
   * @return True if the cell is empty, false if it contains a card
   */
  public boolean isEmpty();

  /**
   * Places a card in this cell if it's a card cell and currently empty.
   *
   * @param card The card to place in this cell.
   */
  public void setCard(CardInterface card);


  /**
   * Gets the card in this cell.
   *
   * @return The card in this cell, or null if the cell is empty.
   */
  public CardInterface getCard();

  /**
   * Gets the row of a Cell.
   *
   * @return row in which the cell is in
   */
  public int getRow();

  /**
   * Gets the column of a Cell.
   *
   * @return column in which the cell is in
   */
  public int getCol();

}
