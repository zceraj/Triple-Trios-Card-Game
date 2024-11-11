package cs3500.tripletrios.strategy;


import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.strategy.Moves;

public class StrategyOne extends AbstractStrategy {

//  /*strat 1 --- flip as many cards in this turn as possible,
//  * choose position and card together
//   */
//
//
 public StrategyOne(GameModel model, IPlayer curPlayer) {
   super(model, curPlayer);
 }

  public Moves getBestMove() {
    Moves bestMove = null;
    int maxFlips = 0;

    for (int row = 0; row < model.getGameGrid().getRows(); row++) {
      for (int col = 0; col < model.getGameGrid().getCols(); col++) {
        Cell cell = model.getGameGrid().getCell(row, col);
        if (cell.isCardCell() && cell.isEmpty()) { // Check if the cell is a valid spot to place a card
          for (CardInterface card : curPlayer.getHand()) {
            int flips = calculateFlips(row, col, card, curPlayer);
            if (flips > maxFlips || (flips == maxFlips && isUpperLeft(row, col, bestMove))) {
              maxFlips = flips;
              bestMove = new Moves(row, col, new Card(card)); // Use copy constructor for safety
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : chooseFallbackMove(currentPlayer);
  }

  private int checkIfFlips(CardInterface card, int row, int col) {

  }


}
