package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;
import cs3500.tripletrios.model.Direction;
import cs3500.tripletrios.model.Cell;

/**
 * A class representing the third strategy of choosing the card that is least
 * likely to be flipped.
 */
public class StrategyThree extends AbstractStrategy {

  /**
   * Constructor for Strategy three.
   *
   * @param model the read-only game model used as the game state
   */
  public StrategyThree(ReadOnlyGameModel model) {
    super(model.getGameGrid());
  }

  /**
   * Finds the least risky card to play.
   *
   * @param computerPlayer the player
   * @return a Move object based on the game state
   */
  @Override
  public Moves getBestMove(IPlayer computerPlayer) {
    int minFlipsRisk = Integer.MAX_VALUE;
    Moves bestMove = null;

    // Iterate through all cells in the grid to determine the best move
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        // If the cell is empty, consider placing a card there
        if (grid.getCell(row, col).isEmpty() && grid.getCell(row, col).isCardCell()) {
          for (CardInterface card : computerPlayer.getCurrentHand()) {
            // Evaluate the flip risk for placing the card in the current cell
            int flipRisk = evaluateFlipRisk(card, row, col, grid);

            if (flipRisk < minFlipsRisk) {
              minFlipsRisk = flipRisk;
              bestMove = new Moves(card, row, col);
            } else if (flipRisk == minFlipsRisk && bestMove != null) {
              bestMove = breakTie(card, row, col, bestMove, computerPlayer);
            }
          }
        }
      }
    }
    return finalMove(computerPlayer, bestMove, grid);
  }

  /**
   * Evaluates the risk of a card being flipped by opponents at the given position.
   *
   * @param card the card being placed
   * @param row  the row where the card is being placed
   * @param col  the column where the card is being placed
   * @param grid the current game grid
   * @return the risk score for the card being flipped
   */
  private int evaluateFlipRisk(CardInterface card, int row, int col, Grid grid) {
    int riskScore = 0;

    // Iterate through all directions to determine the potential risk of being flipped
    for (Direction direction : Direction.values()) {
      Cell adjacentCell = grid.getAdjacentCells(row, col, direction);

      if (adjacentCell != null && !adjacentCell.isEmpty()) {
        CardInterface opponentCard = adjacentCell.getCard();

        if (opponentCard != null && !opponentCard.equals(card)) {
          int opponentAttackValue = intAttackValue(
                  opponentCard.getAttackValue(direction.getOpposite()));
          int currentCardAttackValue = intAttackValue(card.getAttackValue(direction));

          if (opponentAttackValue > currentCardAttackValue) {
            riskScore++;
          }
        }
      }
    }

    return riskScore;
  }
}
