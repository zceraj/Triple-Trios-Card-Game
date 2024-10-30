package tripleTrios.model;

import java.util.EnumMap;

public class Card implements CardInterface {
  private final String cardName;
  private final EnumMap<Direction, Integer> attackValues;
  private int row = -1;
  private int col = -1;


  /**
   * Creates a new card with the given name, attack values, owner, and position.
   * @param cardName The name of the card
   * @param north The attack value of the card in the north direction
   * @param south The attack value of the card in the south direction
   * @param east The attack value of the card in the east direction
   * @param west The attack value of the card in the west direction
   */
  public Card(String cardName, String north, String south, String east, String west) {
    this.cardName = cardName;

    this.attackValues = new EnumMap<>(Direction.class);
    attackValues.put(Direction.NORTH, validateAttackValue(north));
    attackValues.put(Direction.SOUTH, validateAttackValue(south));
    attackValues.put(Direction.EAST, validateAttackValue(east));
    attackValues.put(Direction.WEST, validateAttackValue(west));
  }

  private int validateAttackValue(String value) {
    if ("A".equals(value)) {
      return 10;
    } else if (value.matches("[0-9]")) {
      return Integer.parseInt(value);
    } else {
      throw new IllegalArgumentException("Invalid attack value");
    }
  }


  /**
   * Adds a row to the card.
   * @param row The row to add
   */
  public void addRow(int row) {
    this.row = row;
  }

  /**
   * Adds a column to the card.
   * @param col The column to add
   */
  public void addCol(int col) {
    this.col = col;
  }

  /**
   * Gets the attack value of the card in the given direction.
   * @param direction The direction to get the attack value
   * @return The attack value of the card in the given direction
   */
  public int getAttackValue(Direction direction) {
    return attackValues.get(direction);
  }



  /**
   * Gets the row of the card.
   * @return The row of the card
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column of the card.
   * @return The column of the card
   */
  public int getCol() {
    return col;
  }

}
