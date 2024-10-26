package tripleTrios.model;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
  NORTH, SOUTH, EAST, WEST;

  //Stores the opposite direction of each direction.
  private static final Map<Direction, Direction> opposite = new HashMap<>();

  static {
    opposite.put(NORTH, SOUTH);
    opposite.put(SOUTH, NORTH);
    opposite.put(EAST, WEST);
    opposite.put(WEST, EAST);
  }

  //Retrieves the opposite direction of the current direction
  public Direction getOpposite() {
    return opposite.get(this);
  }
}
