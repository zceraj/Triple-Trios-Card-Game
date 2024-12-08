package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Direction;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

/**
 * Represents a class for the second strategy that places a card in a corner that will maximize the
 * defense value of the corner.
 */
public class StrategyTwo extends AbstractStrategy {

  /**
   * Constructor for the second strategy.
   *
   * @param model a read-only game model representing the game state
   */
  public StrategyTwo(ReadOnlyGameModel model) {
    super(model.getGameGrid());
  }

  /**
   * Gets the best move available based on the current game state.
   *
   * @param computerPlayer the computer-generated player
   * @return a Move object that represents the best move
   */
  @Override
  public MovesInterface getBestMove(IPlayer computerPlayer) {
    MovesInterface bestMove = null;
    int maxDefenseValue = -1;

    int[][] cornerPositions = {
            {0, 0},                               // Top-left corner
            {0, grid.getCols() - 1},              // Top-right corner
            {grid.getRows() - 1, 0},              // Bottom-left corner
            {grid.getRows() - 1, grid.getCols() - 1} // Bottom-right corner
    };

    // Iterate over each corner position to find the best move
    for (int[] corner : cornerPositions) {
      int row = corner[0];
      int col = corner[1];

      // Check if the corner cell is a valid, empty cell for placement
      if (grid.isValidCell(row, col) && grid.getCell(row, col).isEmpty()) {
        for (CardInterface card : computerPlayer.getCurrentHand()) {
          // Calculate the defense value for placing the card in the current corner
          int defenseValue = calculateDefenseValue(card, row, col, grid);

          if (defenseValue > maxDefenseValue) {
            maxDefenseValue = defenseValue;
            bestMove = new Moves(card, row, col);
          } else if (defenseValue == maxDefenseValue && bestMove != null) {
            bestMove = breakTie(card, row, col, bestMove, computerPlayer);
          }
        }
      }
    }
    return finalMove(computerPlayer, bestMove, grid);
  }

  /**
   * Calculate the defense value of placing a card in a corner.
   *
   * @param card the card to be placed
   * @param row  the row where the card is being placed
   * @param col  the column where the card is being placed
   * @param grid the current game grid
   * @return the calculated defense value for placing the card
   */
  private int calculateDefenseValue(CardInterface card, int row, int col, Grid grid) {
    int defenseValue = 0;

    if (row == 0 && col == 0) {
      // Top-left corner
      defenseValue = intAttackValue(card.getAttackValue(Direction.SOUTH))
              + intAttackValue(card.getAttackValue(Direction.EAST));
    } else if (row == 0 && col == grid.getCols() - 1) {
      // Top-right corner
      defenseValue = intAttackValue(card.getAttackValue(Direction.SOUTH))
              + intAttackValue(card.getAttackValue(Direction.WEST));
    } else if (row == grid.getRows() - 1 && col == 0) {
      // Bottom-left corner
      defenseValue = intAttackValue(card.getAttackValue(Direction.NORTH))
              + intAttackValue(card.getAttackValue(Direction.EAST));
    } else if (row == grid.getRows() - 1 && col == grid.getCols() - 1) {
      // Bottom-right corner
      defenseValue = intAttackValue(card.getAttackValue(Direction.NORTH))
              + intAttackValue(card.getAttackValue(Direction.WEST));
    }

    return defenseValue;
  }
}
