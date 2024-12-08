package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Direction;

public interface MovesInterface {
  /**
   * Gets the card.
   *
   * @return The card
   */
  CardInterface getCard();

  /**
   * Gets the row of the card.
   *
   * @return The row of the card
   */
  int getRow();

  /**
   * Gets the column of the card.
   *
   * @return The column of the card
   */
  int getCol();

  /**
   * Gets the score of the card in the given direction.
   *
   * @param direction The direction to get the score of
   * @return The score of the card in the given direction
   */
  int getScore(Direction direction);
}