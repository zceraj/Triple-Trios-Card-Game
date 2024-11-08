package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.Card;

public class Moves {
  private final Card card;
  private final int row;
  private final int col;
  private final int totalFlips;

  public Moves(Card card, int row, int col, int totalFlips) {
    this.card = card;
    this.row = row;
    this.col = col;
    this.totalFlips = totalFlips;
  }

  public Card getCard() {
    return card;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getTotalFlips() {
    return totalFlips;
  }


}
