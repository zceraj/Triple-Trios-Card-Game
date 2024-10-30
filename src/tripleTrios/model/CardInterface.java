package tripleTrios.model;

public interface CardInterface {
  /**
   * Adds a row to the card.
   * @param row The row to add
   */
  void addRow(int row);

  /**
   * Adds a column to the card.
   * @param col The column to add
   */
  void addCol(int col);

  /**
   * Gets the attack value of the card in the given direction.
   * @param direction The direction to get the attack value
   * @return The attack value of the card in the given direction
   */
  int getAttackValue(Direction direction);

  /**
   * Gets the row of the card.
   * @return The row of the card
   */
  int getRow();

  /**
   * Gets the column of the card.
   * @return The column of the card
   */
  int getCol();

}
