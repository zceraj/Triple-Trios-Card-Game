package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.IPlayer;

public interface StrategyInterface {
  Moves getBestMove(IPlayer computerPlayer);
}
