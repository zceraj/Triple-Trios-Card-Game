package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.Direction;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

/**
 * Represents a class for the first strategy that places a card that will maximize the number of
 * opponent's cards that can be flipped.
 */
public class StrategyOne extends AbstractStrategy {

  /**
   * Constructor for the first strategy.
   *
   * @param model a read-only game model to analyze the game state
   */
  public StrategyOne(ReadOnlyGameModel model) {
    super(model.getGameGrid());
  }

  /**
   * Gets the best move available based on the state of the grid.
   *
   * @param computerPlayer the computer generated player
   * @return a Move object representing the best move
   */
  @Override
  public Moves getBestMove(IPlayer computerPlayer) {
    int maxFlips = -1;
    Moves bestMove = null;

    // Iterate through all cells in the grid
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        // Check if the cell is empty
        if (grid.getCell(row, col).isEmpty() && grid.getCell(row, col).isCardCell()) {
          // Iterate through all cards in the computer player's hand
          for (CardInterface card : computerPlayer.getHand()) {
            // Count potential flips for placing the card in this position
            int potentialFlips = countPotentialFlips(card, row, col, grid);
            if (potentialFlips > maxFlips) {
              maxFlips = potentialFlips;
              bestMove = new Moves(card, row, col);
            } else if (potentialFlips == maxFlips && bestMove != null) {
              bestMove = breakTie(card, row, col, bestMove, computerPlayer);
            }
          }
        }
      }
    }
    return finalMove(computerPlayer, bestMove, grid);
  }

  /**
   * Counts the possible flips that would happen if a card was played at the given position.
   *
   * @param card the card being considered for placement
   * @param row  the row to place the card
   * @param col  the column to place the card
   * @param grid the current game grid
   * @return the number of opponent cards that could be flipped
   */
  private int countPotentialFlips(CardInterface card, int row, int col, Grid grid) {
    int flipCount = 0;

    for (Direction direction : Direction.values()) {
      Cell adjacentCell = grid.getAdjacentCells(row, col, direction);
      if (adjacentCell != null && !adjacentCell.isEmpty()) {
        CardInterface opponentCard = adjacentCell.getCard();
        if (opponentCard != null && !opponentCard.equals(card)) {
          int cardAttackValue = card.getAttackValueAsInt(direction);
          int opponentDefenseValue = opponentCard.getAttackValueAsInt(direction.getOpposite());

          if (cardAttackValue > opponentDefenseValue) {
            flipCount++;
          }
        }
      }
    }
    return flipCount;
  }
}
