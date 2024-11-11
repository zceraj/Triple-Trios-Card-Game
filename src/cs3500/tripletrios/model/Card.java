package cs3500.tripletrios.model;

import java.util.EnumMap;

/**
 * Represents a card in the game.
 * Implements the CardInterface.
 */
public class Card implements CardInterface {
  private final String cardName;
  private final EnumMap<Direction, Integer> attackValues;
  private int row = -1;
  private int col = -1;

  /**
   * Creates a new card with the given name and attack values.
   * @param cardName The name of the card
   * @param north The attack value of the card in the north direction
   * @param south The attack value of the card in the south direction
   * @param east The attack value of the card in the east direction
   * @param west The attack value of the card in the west direction
   */
  public Card(String cardName, int north, int south, int east, int west) {
    this.cardName = cardName;

    this.attackValues = new EnumMap<>(Direction.class);
    attackValues.put(Direction.NORTH, north);
    attackValues.put(Direction.SOUTH, south);
    attackValues.put(Direction.EAST, east);
    attackValues.put(Direction.WEST, west);

    if (north < 1
            || north > 10
            || south < 1
            || south > 10
            || east < 1
            || east > 10
            || west < 1
            || west > 10) {
      throw new IllegalArgumentException("Invalid attack value, must be between 1-10.");
    }
  }

  /**
   * Creates a new card with the given name and attack values.
   * @param original The card to copy
   */
  public Card(Card original) {
    this.cardName = original.getCardName();
    this.attackValues = new EnumMap<>(Direction.class);
    for (Direction dir : Direction.values()) {
      this.attackValues.put(dir, original.getAttackValue(dir));
    }
    this.row = original.getRow();
    this.col = original.getCol();
  }




  /**
   * Converts an integer attack value to a string, where 10 is represented by "A".
   * @param value The integer attack value to convert
   * @return The string representation of the attack value ("1"-"9" or "A")
   */
  private String formatAttack(int value) {
    return value == 10 ? "A" : Integer.toString(value);
  }

  /**
   * Sets the row for the card after placement.
   * @param row The row to set
   */
  @Override
  public void addRow(int row) {
    this.row = row;
  }

  /**
   * Sets the column for the card after placement.
   * @param col The column to set
   */
  @Override
  public void addCol(int col) {
    this.col = col;
  }

  /**
   * Gets the attack value of the card in the given direction as a string.
   * @param direction The direction to get the attack value
   * @return The attack value of the card in the given direction ("1"-"9" or "A").
   */
  @Override
  public String getAttackValue(Direction direction) {

    return formatAttack(attackValues.get(direction));
  }

  /**
   * Gets the row of the card.
   * @return The row of the card
   */
  @Override
  public int getRow() {
    return row;
  }

  /**
   * Gets the column of the card.
   * @return The column of the card
   */
  @Override
  public int getCol() {
    return col;
  }

  /**
   * Gets the column of the card.
   * @return The name of the card.
   */
  public String getCardName() {
    return cardName;
  }

  /**
   * formats the card as the name of the card and all of its attack values.
   * @return the card as a string.
   */
  public String toString() {
    StringBuilder attackValuesString = new StringBuilder();
    for (Integer value : attackValues.values()) {
      attackValuesString.append(value.toString());
      attackValuesString.append(" ");
    }
    return cardName + " " + attackValuesString;
  }
}
