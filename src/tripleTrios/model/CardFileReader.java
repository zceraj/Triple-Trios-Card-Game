package tripleTrios.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class responsible for reading card data from a specified file.
 * Implements the card reader interface to provide functionality
 * for loading a list of cards.
 */
public class CardFileReader implements CardReader {
  private final List<Card> cards;

  /**
   * Constructs a {@code CardFileReader} instance and parses the card data from the given file.
   *
   * @param filePath the path to the card database file.
   * @throws IOException if an error occurs while reading the file.
   */
  public CardFileReader(String filePath) throws IOException {
    cards = new ArrayList<>();
    parseCardFile(filePath);
  }

  /**
   * Parses the card data from the specified file.
   * Each line of the file is expected to contain the card name followed by
   * four integers representing values for each direction (NORTH, SOUTH, EAST, WEST).
   *
   * @param filePath the path to the card database file.
   * @throws IOException if an error occurs while reading the file.
   */
  private void parseCardFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(" ");
        if (parts.length == 5) {
          String cardName = parts[0];
          int north = Integer.parseInt(parts[1]);
          int south = Integer.parseInt(parts[2]);
          int east = Integer.parseInt(parts[3]);
          int west = Integer.parseInt(parts[4]);

          Card card = new Card(cardName, north, south, east, west);
          cards.add(card);
        }
      }
    } catch (IOException | NumberFormatException e) {
      System.err.println("Error reading card database file: " + e.getMessage());
      throw e;
    }
  }

  /**
   * Retrieves the list of cards that were loaded from the file.
   *
   * @return a list of cards.
   */
  public List<Card> getCardDatabase() {
    return cards;
  }
}
