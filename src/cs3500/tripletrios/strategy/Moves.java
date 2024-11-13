package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;

public class Moves {
  private final CardInterface card;
  private final int row;
  private final int col;

  public Moves(CardInterface card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;

  }


  public CardInterface getCard() {
    return card;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }



}
