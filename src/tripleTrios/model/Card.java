package tripleTrios.model;

import java.util.EnumMap;

public class Card {
  private final String cardName;
  private final EnumMap<Direction, Integer> attackValues;
  private final int row;
  private final int col;


  /**
   * Creates a new card with the given name, attack values, owner, and position.
   * @param cardName The name of the card
   * @param north The attack value of the card in the north direction
   * @param south The attack value of the card in the south direction
   * @param east The attack value of the card in the east direction
   * @param west The attack value of the card in the west direction
   * @param row The row of the card
   * @param col The column of the card
   */
  public Card(String cardName, int north, int south, int east, int west, int row, int col) {
    this.cardName = cardName;
    this.row = row;
    this.col = col;

    this.attackValues = new EnumMap<>(Direction.class);
    attackValues.put(Direction.NORTH, north);
    attackValues.put(Direction.SOUTH, south);
    attackValues.put(Direction.EAST, east);
    attackValues.put(Direction.WEST, west);
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
