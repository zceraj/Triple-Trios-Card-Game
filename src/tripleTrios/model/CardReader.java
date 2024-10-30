package tripleTrios.model;

import java.util.Map;

public interface CardReader {

  /**
   * returns a set of cards that can be used in the game.
   * @return a map of cards.
   */
  public List<C> getCardDatabase();
}
