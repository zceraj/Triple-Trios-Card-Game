package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Direction;

/**
 * Represents a move in the game.
 */
public class Moves {
  private final CardInterface card;
  private final int row;
  private final int col;

  /**
   * Constructs a Moves object.
   */
  public Moves(CardInterface card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the card.
   *
   * @return The card
   */
  public CardInterface getCard() {
    return card;
  }

  /**
   * Gets the row of the card.
   *
   * @return The row of the card
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column of the card.
   *
   * @return The column of the card
   */
  public int getCol() {
    return col;
  }

  /**
   * Gets the score of the card in the given direction.
   *
   * @param direction The direction to get the score of
   * @return The score of the card in the given direction
   */
  public int getScore(Direction direction) {
    return Integer.parseInt(card.getAttackValue(direction));
  }
}

