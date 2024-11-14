package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.IPlayer;

/**
 * Represents a strategy interface for the computer player to make moves.
 */
public interface StrategyInterface {

  Moves getBestMove(IPlayer computerPlayer);
}
