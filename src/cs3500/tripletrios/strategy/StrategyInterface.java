package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.IPlayer;

/**
 * Represents a class for the strategy.
 * Represents a strategy interface for the computer player to make moves.
 */
public interface StrategyInterface {

  /**
   * the best move for a computer player.
   *
   * @param computerPlayer an AI player
   * @return a move that is superior.
   */
  MovesInterface getBestMove(IPlayer computerPlayer);
}
