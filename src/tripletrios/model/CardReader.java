package tripletrios.model;

import java.util.List;

public interface CardReader <C extends CardInterface>{

  /**
   * returns a set of cards that can be used in the game.
   * @return a list of cards in the file.
   */
  List<C> getCardDatabase();
}
