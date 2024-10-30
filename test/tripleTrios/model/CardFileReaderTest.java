package tripleTrios.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardFileReaderTest {
  private static final String VALID_FILE = "test_cards.txt";
  private static final String INVALID_FILE_PATH = "invalid_file.txt";
  private static final String BAD_FILE_PATH = "malformed_cards.txt";

  @BeforeEach
  void setUp() throws IOException {
    // Create a valid file for testing
    try (FileWriter writer = new FileWriter(VALID_FILE)) {
      writer.write("Card1 1 2 3 4\n");
      writer.write("Card2 5 6 7 8\n");
    }

    // Create a malformed file for testing
    try (FileWriter writer = new FileWriter(BAD_FILE_PATH)) {
      writer.write("Card3 9 10 11\n"); // Missing one parameter
      writer.write("Card4 12 13 14 15\n");
    }
  }

  @Test
  void testReadValidCardFile() throws IOException {
    CardFileReader reader = new CardFileReader(VALID_FILE);
    List<Card> cards = reader.getCardDatabase();

    assertEquals(2, cards.size());
    assertEquals("Card1", cards.get(0).getCardName());
    assertEquals(1, cards.get(0).getAttackValue(Direction.NORTH));
    assertEquals(2, cards.get(0).getAttackValue(Direction.SOUTH));
    assertEquals(3, cards.get(0).getAttackValue(Direction.EAST));
    assertEquals(4, cards.get(0).getAttackValue(Direction.WEST));

    assertEquals("Card2", cards.get(1).getCardName());
    assertEquals(5, cards.get(1).getAttackValue(Direction.NORTH));
    assertEquals(6, cards.get(1).getAttackValue(Direction.SOUTH));
    assertEquals(7, cards.get(1).getAttackValue(Direction.EAST));
    assertEquals(8, cards.get(1).getAttackValue(Direction.WEST));
  }

  @Test
  void testReadInvalidCardFile() {
    assertThrows(IOException.class, () -> {
      new CardFileReader(INVALID_FILE_PATH);
    });
  }

  @Test
  void testReadMalformedCardFile() {
    try {
      CardFileReader reader = new CardFileReader(BAD_FILE_PATH);
      List<Card> cards = reader.getCardDatabase();

      // Even if one line is malformed, the valid lines should still be processed
      assertEquals(1, cards.size());
      assertEquals("Card4", cards.get(0).getCardName());
      assertEquals(12, cards.get(0).getAttackValue(Direction.NORTH));
      assertEquals(13, cards.get(0).getAttackValue(Direction.SOUTH));
      assertEquals(14, cards.get(0).getAttackValue(Direction.EAST));
      assertEquals(15, cards.get(0).getAttackValue(Direction.WEST));
    } catch (IOException e) {
      fail("IOException should not be thrown for malformed file");
    }
  }

  @AfterEach
  void tearDown() {
    new File(VALID_FILE).delete();
    new File(BAD_FILE_PATH).delete();
  }
}
