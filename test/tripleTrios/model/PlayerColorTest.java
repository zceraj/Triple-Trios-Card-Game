package tripleTrios.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlayerColorTest {

  @Test
  void testSetColorWithValidColors() {
    assertDoesNotThrow(() -> PlayerColor.RED.setColor("red"));
    assertDoesNotThrow(() -> PlayerColor.BLUE.setColor("blue"));
  }

  @Test
  void testSetColorWithInvalidColor() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      PlayerColor.RED.setColor("green");
    });
    assertEquals("Unknown color: green", thrown.getMessage());
  }

  @Test
  void testSetColorWithInvalidNumbers() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      PlayerColor.RED.setColor("123");
    });
    assertEquals("Unknown color: 123", thrown.getMessage());
  }

  @Test
  void testSetColorWithInvalidLettersAndNumbers() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      PlayerColor.RED.setColor("blue123");
    });
    assertEquals("Unknown color: blue123", thrown.getMessage());
  }

  @Test
  void testToStringMethod() {
    // Check the formatted string representation of each color
    assertEquals("Red", PlayerColor.RED.toString());
    assertEquals("Blue", PlayerColor.BLUE.toString());
  }
}
