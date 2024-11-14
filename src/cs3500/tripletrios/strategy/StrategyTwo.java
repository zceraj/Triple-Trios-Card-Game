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
public class StrategyTwo extends AbstractStrategy implements StrategyInterface {
  private final ReadOnlyGameModel model;

  /**
   * Constructor for the second strategy.
   *
   * @param model a read-only game model  representing the game state
   */
  public StrategyTwo(ReadOnlyGameModel model) {
    super(model.getGameGrid());
    this.model = model;
  }

  /**
   * Gets the best move available based off the game state.
   *
   * @param computerPlayer the computer generated player
   * @return a Move object that represents the best move
   */
  @Override
  public Moves getBestMove(IPlayer computerPlayer) {
    Moves bestMove = null;
    int maxDefenseValue = -1;
    Grid grid = model.getGameGrid();

    int[][] cornerPositions = {
            {0, 0},                               // Top-left corner
            {0, grid.getCols() - 1},              // Top-right corner
            {grid.getRows() - 1, 0},              // Bottom-left corner
            {grid.getRows() - 1, grid.getCols() - 1} // Bottom-right corner
    };

    for (int[] corner : cornerPositions) {
      int row = corner[0];
      int col = corner[1];

      if (grid.isValidCell(row, col) && grid.getCell(row, col).isEmpty()) {
        for (CardInterface card : computerPlayer.getHand()) {
          int defenseValue = calculateDefenseValue(card, row, col);

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


  // Calculate the defense value of placing a card in a corner.
  private int calculateDefenseValue(CardInterface card, int row, int col) {
    int defenseValue = 0;
    Grid gridCopy = new Grid(model.getGameGrid());

    if (row == 0 && col == 0) {
      defenseValue = intAttackValue(card.getAttackValue(Direction.SOUTH))
              + intAttackValue(card.getAttackValue(Direction.EAST));
    } else if (row == 0 && col == gridCopy.getCols() - 1) {
      defenseValue = intAttackValue(card.getAttackValue(Direction.SOUTH))
              + intAttackValue(card.getAttackValue(Direction.WEST));
    } else if (row == gridCopy.getRows() - 1 && col == 0) {
      defenseValue = intAttackValue(card.getAttackValue(Direction.NORTH))
              + intAttackValue(card.getAttackValue(Direction.EAST));
    } else if (row == gridCopy.getRows() - 1 && col == gridCopy.getCols() - 1) {
      defenseValue = intAttackValue(card.getAttackValue(Direction.NORTH))
              + intAttackValue(card.getAttackValue(Direction.WEST));
    }

    return defenseValue;
  }

<<<<<<< HEAD

=======
>>>>>>> b4c1fae1b969592c6a89b6619d5941a5a7c96b5a
}