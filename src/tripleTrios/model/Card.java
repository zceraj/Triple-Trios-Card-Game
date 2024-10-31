package tripleTrios.model;

import java.util.EnumMap;

/**
 * Represents a card in the game.
 * Implements the CardInterface.
 */
public class Card implements CardInterface {
  private final String cardName;
  private final EnumMap<Direction, String> attackValues;
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
    attackValues.put(Direction.NORTH, stringToIntAttack(north));
    attackValues.put(Direction.SOUTH, stringToIntAttack(south));
    attackValues.put(Direction.EAST, stringToIntAttack(east));
    attackValues.put(Direction.WEST, stringToIntAttack(west));
  }

  /**
   * Converts an integer attack value to a string, where 10 is represented by "A".
   * @param value The integer attack value to convert
   * @return The string representation of the attack value ("1"-"9" or "A")
   */
  private String stringToIntAttack(int value) {
    if (value == 10) {
      return "A";
    } else if (value >= 1 && value <= 9) {
      return Integer.toString(value);
    } else {
      throw new IllegalArgumentException("Invalid attack value, must be between 1-9 or A.");
    }
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
    return attackValues.get(direction);
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

}
