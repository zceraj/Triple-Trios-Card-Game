package tripleTrios.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CardFileReader implements CardReader {
  private final List<Card> cards;

  public CardFileReader(String filePath) throws IOException {
    cards = new ArrayList<>();
    parseCardFile(filePath);
  }

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

  public List<Card> getCardDatabase() {
    return cards;
  }
}
