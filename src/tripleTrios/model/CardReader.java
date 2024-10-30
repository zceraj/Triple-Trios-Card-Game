package tripleTrios.model;

import java.util.List;
import java.util.Map;

public interface CardReader <C extends CardInterface>{

  /**
   * returns a set of cards that can be used in the game.
   * @return a list of cards in the file.
   */
  List<C> getCardDatabase();
}
