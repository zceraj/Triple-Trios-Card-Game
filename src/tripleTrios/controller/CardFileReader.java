package tripleTrios.controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tripleTrios.model.Card;


public class CardFileReader {
  private Map<String, Card> cards;

  public CardFileReader(String filePath) throws IOException {
    cards = new HashMap<>();
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
          cards.put(cardName, card);
        }
      }
    } catch (IOException | NumberFormatException e) {
      System.err.println("Error reading card database file: " + e.getMessage());
      throw e;
    }
  }

  public Map<String, Card> getCardDatabase() {
    return cardDatabase;
  }
}
