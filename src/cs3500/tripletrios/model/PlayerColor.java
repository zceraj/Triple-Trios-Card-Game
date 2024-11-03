package cs3500.tripletrios.model;

/**
 * Represents the colors that can be assigned to a player in the Triple Trios game.
 * The available colors are RED and BLUE.
 */
public enum PlayerColor {

  /** Represents the color red. */
  RED,

  /** Represents the color blue. */
  BLUE;

  /**
   * Sets the color of the player based on the provided string representation.
   *
   * @param color the string representation of the color, which will be converted
   *              to uppercase to match the enum constants.
   * @throws IllegalArgumentException if the provided color does not match any
   *                                  of the defined colors in this enum.
   */
  public void setColor(String color) {
    try {
      PlayerColor.valueOf(color.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unknown color: " + color);
    }
  }

  /**
   * Returns a string representation of the color.
   * The format is the first letter capitalized followed by lowercase letters.
   *
   * @return the string representation of the color.
   */
  @Override
  public String toString() {
    return name().charAt(0) + name().substring(1).toLowerCase();
  }
}