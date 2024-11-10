//package cs3500.tripletrios.strategy;
//
//import cs3500.tripletrios.model.Card;
//import cs3500.tripletrios.model.Cell;
//import cs3500.tripletrios.model.GameModel;
//import cs3500.tripletrios.model.Grid;
//import cs3500.tripletrios.model.IPlayer;
//
//public class StrategyOne extends AbstractStrategy {
//
//  /*strat 1 --- flip as many cards in this turn as possible,
//  * choose position and card together
//   */
//
//
//  public StrategyOne(GameModel model, IPlayer curPlayer) {
//    super(model, curPlayer);
//  }
//
//  public Moves getBestMove() {
//    int maxFlips = -1;
//    Moves bestMove = null;
//
//    for (Card card: curPlayer.getHand()) {
//      Grid gird = model.getGameGrid();
//      for (int row = 0; row < gird.getRows(); row++) {
//        for (int col = 0; col < grid.getCols(); col++) {
//          Cell cell = grid.getCell(row, col);
//
//          if (cell.isCardCell && cell.isEmpty()) {
//            int flips = checkIfFlips(card, row, col);
//            if (flips > maxFlips) {
//              maxFlips = flips;
//              bestMove = new Moves(card, row, col, flips);
//            } else if (flips == maxFlips) {
//              //
//            }
//          }
//        }
//      }
//    }
//  }
//
//  private int checkIfFlips(Card card, int row, int col) {
//  }
//
//
//
//  /*Start 2
//  go for corners, cards in corners only expose two attack values
//  * and are harder to flip
//  * consider which card is hardest to flip in that corner
//  * if in top right most corner exposes south and west values must eb highest
//  * if in top left most corner exposes south and east values must be highest
//  * if in the bottom right most corner exposes north and west values must be highest
//  * if in the bottom left most corner exposes north and east values must be highest
//   */
//
//  /*Strat 3
//  * choose cards less likley t be flipped in general
//  * consider each position for each card, for each direction
//  * figuring out how many of the opponents card can flip them
//  * card and position combination with the smallest chance of being flipped
//   */
//
//  /*  Strat 4
//  *moves that leaves opponent in situation with no good moves
//  * minimizes the maxiumum move the opponent can make
//  * calculate best move and opponenet can make -> must make soem guess to
//  *     what strategy the opponent is using
//   */
//
//  /*Break ties
//  *if there are multiple best moves that can be chosen in a single stratgy
//  * break ties by choosing the move with uppermost-leftmost coordinate
//  * for the position then choose the best card for tha position
//  * with an index closest to the 0 in the hand
//  *
//  * if no valid moves: choose the upper-mpst left most open position
//  * and the card at index 0
//  *
//  * hint: keep un mid that a stargey needs to know whcih player is tryig to pick a move for
//  *
//   */
//
//
//  /*STRATEGY TO DO LIST:
//  * 1. implement the the strategies
//  * 2. implement the tie breakers
//  *
//
//   */
//
//
//
//}
