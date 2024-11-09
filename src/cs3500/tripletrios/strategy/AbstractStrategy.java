package cs3500.tripletrios.strategy;

import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;

public class AbstractStrategy {

  protected GameModel model;
  protected IPlayer curPlayer;

  public AbstractStrategy(GameModel model, IPlayer curPlayer) {
    this.model = model;
    this.curPlayer = curPlayer;
  }

  protected int evaluateFlips(Card card, int row, int col) {
    return 0;
  }

}
