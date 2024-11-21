package tripletrios.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.controller.CardFileReader;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardFileReaderTest {
  private static final String VALID_FILE = "."
          + File.separator
          + "TESTINGFILES"
          + File.separator
          + "test_cards.txt";
  private static final String INVALID_FILE_PATH = "invalid_file.txt";
  private static final String BAD_FILE_PATH = "."
          + File.separator
          + "TESTINGFILES"
          + File.separator
          + "bad_card.txt";


  @Test
  void testReadValidCardFile() throws IOException {
    CardFileReader reader = new CardFileReader(VALID_FILE);
    List<CardInterface> cards = reader.getCardDatabase();

    assertEquals(2, cards.size());
    assertEquals("Card1", cards.get(0).getCardName());
    assertEquals("1", cards.get(0).getAttackValue(Direction.NORTH));
    assertEquals("2", cards.get(0).getAttackValue(Direction.SOUTH));
    assertEquals("3", cards.get(0).getAttackValue(Direction.EAST));
    assertEquals("4", cards.get(0).getAttackValue(Direction.WEST));

    assertEquals("Card2", cards.get(1).getCardName());
    assertEquals("5", cards.get(1).getAttackValue(Direction.NORTH));
    assertEquals("6", cards.get(1).getAttackValue(Direction.SOUTH));
    assertEquals("7", cards.get(1).getAttackValue(Direction.EAST));
    assertEquals("8", cards.get(1).getAttackValue(Direction.WEST));
  }

  @Test
  void testReadInvalidCardFile() {
    assertThrows(IOException.class, () -> {
      new CardFileReader(INVALID_FILE_PATH);
    });
  }

  @Test
  void testReadMalformedCardFile() {
    assertThrows(IllegalArgumentException.class, () -> {
      new CardFileReader(BAD_FILE_PATH);
    });
  }
}
