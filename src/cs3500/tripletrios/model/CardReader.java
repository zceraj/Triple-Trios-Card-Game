package cs3500.tripletrios.model;

import java.util.List;

/**
 * Represents a player in the game.
 * @param <C> generic type to extend for card interface
 */
public interface CardReader<C extends CardInterface> {

  /**
   * returns a set of cards that can be used in the game.
   * @return a list of cards in the file.
   */
  List<C> getCardDatabase();
}
