package cs3500.tripletrios.strategy;


import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.Direction;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

public class StrategyOne extends AbstractStrategy implements StrategyInterface {

  private final ReadOnlyGameModel model;


  /**
   * Constructor for the first strategy.
   *
   * @param model a read-only game model to analyze the game state
   */
  public StrategyOne(ReadOnlyGameModel model) {
    this.model = model;
  }


  /**
   * Gets the best move available based of the state of the grid.
   * @param computerPlayer  the computer generated player
   * @return a Move object
   */
  @Override
  public Moves getBestMove(IPlayer computerPlayer) {
    int maxFlips = -1;
    Moves bestMove = null;
    Grid grid = model.getGameGrid();

    // Iterate through all cells on the grid
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (grid.getCell(row, col).isEmpty()) { // Check if the cell is empty for placement
          for (CardInterface card : computerPlayer.getHand()) {
            int potentialFlips = countPotentialFlips(card, row, col);
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

    // Fallback mechanism: if no best move was found, choose the upper-left most open cell and the first card
    return finalMove(computerPlayer, bestMove, grid);
  }


  //counts the possible flips that would happen if a card was played
  private int countPotentialFlips(CardInterface card, int row, int col) {
    int flipCount = 0;
    Grid gridCopy = new Grid(model.getGameGrid()); // Create a deep copy of the grid

    for (Direction direction : Direction.values()) {
      Cell adjacentCell = gridCopy.getAdjacentCells(row, col, direction);
      if (adjacentCell != null && !adjacentCell.isEmpty()) {
        CardInterface opponentCard = adjacentCell.getCard();
        if (opponentCard != null && !opponentCard.equals(card)) {
          int cardAttackValue = intAttackValue(card.getAttackValue(direction));
          int opponentDefenseValue = intAttackValue(opponentCard.getAttackValue(direction.getOpposite()));

          if (cardAttackValue > opponentDefenseValue) {
            flipCount++;
          }
        }
      }
    }

    return flipCount;
  }


}






