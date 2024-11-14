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
public class StrategyThree extends AbstractStrategy implements StrategyInterface {

  private final ReadOnlyGameModel model;


  /**
   * Constructor for Strategy three.
   * @param model the read-only game model used as the game state
   */
  public StrategyThree(ReadOnlyGameModel model) {
    super(model.getGameGrid());
    this.model = model;
  }

  /**
   * Finds the least risky card to play.
   * @param computerPlayer the player
   * @return a Move object based off the game state
   */
  @Override
  public Moves getBestMove(IPlayer computerPlayer) {
    int minFlipsRisk = Integer.MAX_VALUE;
    Moves bestMove = null;
    Grid grid = model.getGameGrid();

    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (grid.getCell(row, col).isEmpty()) {
          for (CardInterface card : computerPlayer.getHand()) {
            int flipRisk = evaluateFlipRisk(card, row, col);

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



  // Evaluates the risk of a card being flipped by opponents at the given position.
  private int evaluateFlipRisk(CardInterface card, int row, int col) {
    int riskScore = 0;
    Grid gridCopy = new Grid(model.getGameGrid());

    for (Direction direction : Direction.values()) {
      Cell adjacentCell = gridCopy.getAdjacentCells(row, col, direction);

      if (adjacentCell != null && !adjacentCell.isEmpty()) {
        CardInterface opponentCard = adjacentCell.getCard();

        if (opponentCard != null && !opponentCard.equals(card)) {
          int opponentAttackValue = intAttackValue(opponentCard.getAttackValue(direction.getOpposite()));
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

