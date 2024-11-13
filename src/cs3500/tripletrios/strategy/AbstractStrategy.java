package cs3500.tripletrios.strategy;


import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;

/**
 * An abstract class of the common methods between the strategies
 */
public class AbstractStrategy  {

  // Converts the attack value to an integer
  protected int intAttackValue(String attackValue) {
    if ("A".equals(attackValue)) {
      return 10;
    } else {
      return Integer.parseInt(attackValue);
    }
  }

  // Break ties by position and card index
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

  // Fallback mechanism: if no best move was found, choose the upper-left most open cell and the first card
  protected static Moves finalMove(IPlayer computerPlayer, Moves bestMove, Grid grid) {
    if (bestMove == null) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getCols(); col++) {
          if (grid.getCell(row, col).isEmpty()) {
            return new Moves(computerPlayer.getHand().get(0), row, col);
          }
        }
      }
    }

    return bestMove;
  }
}
