package cs3500.tripletrios.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;

/**
 * Represents a class for the chain strategy that applies a list of strategies.
 */
public class ChainStrategy extends AbstractStrategy implements StrategyInterface {

  private final List<StrategyInterface> strategies;

  /**
   * Constructor for the ChainStrategy with a list of
   * strategies.
   *
   * @param grid the grid of the game
   */
  public ChainStrategy(Grid grid) {
    super(grid);
    this.strategies = new ArrayList<>();
  }

  /**
   * Adds a strategy to the list of strategies.
   * @param strategy the strategy to add
   */
  public void addStrategy(StrategyInterface strategy) {
    strategies.add(strategy);
  }

  /**
   * Applies the strategies sequentially and returns the best move.
   * @param computerPlayer The computer player using the strategy.
   * @return The best move found, or a fallback move if none found.
   */
  @Override
  public Moves getBestMove(IPlayer computerPlayer) {
    if (strategies.isEmpty()) {
      return null;
    }

    Moves bestMove = null;
    for (StrategyInterface strategy : strategies) {
      bestMove = strategy.getBestMove(computerPlayer);
      if (bestMove != null) {
        break;
      }
    }
    return finalMove(computerPlayer, bestMove, grid);
  }

}
