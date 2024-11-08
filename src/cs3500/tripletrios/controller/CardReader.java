package cs3500.tripletrios.controller;

import java.util.List;

import cs3500.tripletrios.model.CardInterface;

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
