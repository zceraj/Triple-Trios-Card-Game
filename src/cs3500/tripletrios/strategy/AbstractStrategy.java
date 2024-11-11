package cs3500.tripletrios.strategy;

import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;

public class AbstractStrategy {

  protected GameModel model;
  protected IPlayer curPlayer;

  public AbstractStrategy(GameModel model, IPlayer curPlayer) {
    this.model = model;
    this.curPlayer = curPlayer;
  }

  protected int evaluateFlips(Card card, int row, int col) {
    return 0;
  }


//  /*Break ties
//  *if there are multiple best moves that can be chosen in a single stratgy
//  * break ties by choosing the move with uppermost-leftmost coordinate
//  * for the position then choose the best card for tha position
//  * with an index closest to the 0 in the hand
//  *
//  * if no valid moves: choose the upper-mpst left most open position
//  * and the card at index 0
//  *
//  * hint: keep un mid that a stargey needs to know whcih player is tryig to pick a move for
//  *
//   */

}
