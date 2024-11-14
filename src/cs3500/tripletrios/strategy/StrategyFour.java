package cs3500.tripletrios.strategy;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

/**
 * Represents a class for the fourth strategy to place a card that will minimize the opponent's
 * ability to flip cards.
 */
public class StrategyFour extends AbstractStrategy implements StrategyInterface {

  private final ReadOnlyGameModel model;
  private final StrategyInterface oppStrategy;

  /**
   * Constructor for Strategy four.
   *
   * @param model       the read-only game model used as a game state
   * @param oppStrategy the strategy the opponent might use
   */
  public StrategyFour(ReadOnlyGameModel model, StrategyInterface oppStrategy) {
    // Pass the grid from the model to the abstract strategy constructor
    super(model.getGameGrid());
    this.model = model;
    this.oppStrategy = oppStrategy;
  }

  @Override
  public Moves getBestMove(IPlayer computerPlayer) {
    int minOpponentMaxScore = Integer.MAX_VALUE;
    Moves bestMove = null;
    Grid grid = model.getGameGrid();

    // Iterate through the grid to find available cells
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (grid.getCell(row, col).isEmpty() && grid.getCell(row, col).isCardCell()) {
          for (CardInterface card : computerPlayer.getHand()) {
            Grid simulatedGrid = new Grid(model.getGameGrid());
            Cell simulatedCell = simulatedGrid.getCell(row, col);
            simulatedCell.setCard(card);
            simulatedGrid.updateCell(row, col, simulatedCell);

            IPlayer opponent = model.getOtherPlayer();
            ReadOnlyGameModel simulatedModel = createNewModel(simulatedGrid, opponent);
            Moves opponentBestMove = oppStrategy.getBestMove(opponent);

            int opponentMaxScore = evaluateMove(opponentBestMove, simulatedModel);

            if (opponentMaxScore < minOpponentMaxScore) {
              minOpponentMaxScore = opponentMaxScore;
              bestMove = new Moves(card, row, col);
            } else if (opponentMaxScore == minOpponentMaxScore && bestMove != null) {
              bestMove = breakTie(card, row, col, bestMove, computerPlayer);
            }
          }
        }
      }
    }
    return bestMove;
  }




  /**
   * Creates a simulated model with a modified grid and specified current player.
   *
   * @param grid The grid after the computer's simulated move.
   * @param currentPlayer The opponent who will be making the next move.
   * @return A read-only game model representing the simulated game state.
   */
  private ReadOnlyGameModel createNewModel(Grid grid, IPlayer currentPlayer) {
    return new GameModelImpl(grid, currentPlayer, model.getOtherPlayer());
  }

  /**
   * Evaluates the potential score of a move for the opponent.
   *
   * @param move The opponent's best move.
   * @param simulatedModel The game model after the computer's move.
   * @return The score representing how advantageous the move is for the opponent.
   */
  private int evaluateMove(Moves move, ReadOnlyGameModel simulatedModel) {
    if (move == null) {
      return 0;
    }
    simulatedModel.getGameGrid().getCell(move.getRow(), move.getCol()).setCard(move.getCard());
    return simulatedModel.getScore(simulatedModel.getCurPlayer());
  }
}
