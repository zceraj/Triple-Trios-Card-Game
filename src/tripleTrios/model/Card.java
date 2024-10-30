package tripleTrios.model;
import java.util.EnumMap;

public class Card implements CardInterface {
  private final String cardName;
  private final EnumMap<Direction, String> attackValues; // Stores attack values as strings
  private int row = -1;
  private int col = -1;

  /**
   * Creates a new card with the given name and attack values.
   * @param cardName The name of the card
   * @param north The attack value in the north direction (1-10)
   * @param south The attack value in the south direction (1-10)
   * @param east The attack value in the east direction (1-10)
   * @param west The attack value in the west direction (1-10)
   */
  public Card(String cardName, int north, int south, int east, int west) {
    this.cardName = cardName;
    this.attackValues = new EnumMap<>(Direction.class);

    attackValues.put(Direction.NORTH, tenToStringValue(north));
    attackValues.put(Direction.SOUTH, tenToStringValue(south));
    attackValues.put(Direction.EAST, tenToStringValue(east));
    attackValues.put(Direction.WEST, tenToStringValue(west));
  }


  @Override
  public void addRow(int row) {

  }

  @Override
  public void addCol(int col) {

  }

  /**
   * Gets the attack value of the card in the specified direction as a string.
   * @param direction The direction to get the attack value
   * @return The attack value in the specified direction ("1"-"9" or "A")
   */
  public String getAttackValue(Direction direction) {
    return attackValues.get(direction);
  }

  // Methods to set row and column after placement
  public void setRow(int row) {
    this.row = row;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  //Converts an integer attack value to its string representation.
  private String tenToStringValue(int value) {
    if (value == 10) {
      return "A";
    } else {
      return Integer.toString(value);
    }
  }

  @Override
  public String toString() {
    return String.format("%s: N=%s S=%s E=%s W=%s", cardName,
            attackValues.get(Direction.NORTH), attackValues.get(Direction.SOUTH),
            attackValues.get(Direction.EAST), attackValues.get(Direction.WEST));
  }
}
