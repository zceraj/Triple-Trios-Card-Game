package cs3500.tripletrios.strategy;

import java.util.List;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;

public class AbstractStrategy {

  // Parses the attack value and converts "A" to 10.
  protected int intAttackValue(String attackValue) {
    if ("A".equals(attackValue)) {
      return 10;
    } else {
      return Integer.parseInt(attackValue);
    }
  }

  protected Moves breakTie(CardInterface card, int row, int col, Moves bestMove, IPlayer player) {
    // Check for uppermost-leftmost coordinate
    if (row < bestMove.getRow() || (row == bestMove.getRow() && col < bestMove.getCol())) {
      return new Moves(card, row, col);
    }
    // If coordinates are the same, choose the card with the lowest index in the hand
    if (row == bestMove.getRow() && col == bestMove.getCol()) {
      int cardIndex = player.getHand().indexOf(card);
      int bestMoveCardIndex = player.getHand().indexOf(bestMove.getCard());
      if (cardIndex < bestMoveCardIndex) {
        return new Moves(card, row, col);
      }
    }
    return bestMove;
  }



  // Abstract method for calculating defense value, to be implemented by each strategy


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
