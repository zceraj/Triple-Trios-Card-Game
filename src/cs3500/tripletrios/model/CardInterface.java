package cs3500.tripletrios.model;

/**
 * Represents a card in the game.
 * This class can be expanded to include more functionality for the card.
 */
public interface CardInterface {
  /**
   * Sets the row for the card after placement.
   * @param row The row to set
   */
  void addRow(int row);

  /**
   * Sets the column for the card after placement.
   * @param col The column to set
   */
  void addCol(int col);

  /**
   * Gets the attack value of the card in the given direction as a string.
   * @param direction The direction to get the attack value
   * @return The attack value in the given direction as a string ("1"-"9" or "A").
   */
  String getAttackValue(Direction direction);

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


  /**
   * Gets the name of the card.
   * @return The name of the card
   */
  String getCardName();

  /**
   * Gets the attack value of the card in the given direction as an integer.
   * @param direction The direction to get the attack value
   * @return The attack value of the card in the given direction (1-10).
   */
  int getAttackValueAsInt(Direction direction);


  /**
   * formats the card as the name of the card and all of its attack values.
   * @return the card as a string.
   */
  String toString();
}
