package tripletrios.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing the four cardinal directions: NORTH, SOUTH, EAST, and WEST.
 * Provides functionality to retrieve the opposite direction for each direction.
 */
public enum Direction {
  // Represents the north direction.
  NORTH,
  //Represents the south direction.
  SOUTH,
  // Represents the east direction.
  EAST,
  // Represents the west direction.
  WEST;

  // Stores the opposite direction of each direction.
  private static final Map<Direction, Direction> opposite = new HashMap<>();

  static {
    opposite.put(NORTH, SOUTH);
    opposite.put(SOUTH, NORTH);
    opposite.put(EAST, WEST);
    opposite.put(WEST, EAST);
  }

  /**
   * Retrieves the opposite direction of the current direction.
   *
   * @return The opposite direction of the current direction.
   */
  public Direction getOpposite() {
    return opposite.get(this);
  }
}
