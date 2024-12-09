package cs3500.tripletrios.model;

import cs3500.tripletrios.provider.model.ReadOnlyThreeTriosModelInterface;
import cs3500.tripletrios.provider.model.card.CustomCard;
import cs3500.tripletrios.provider.model.cell.CellState;
import cs3500.tripletrios.provider.model.grid.Grid;
import cs3500.tripletrios.provider.model.GameState;
import cs3500.tripletrios.provider.model.PlayerColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to make GameModel compatible with the provider's ReadOnlyThreeTriosModelInterface.
 */
public class ModelProviderAdapter implements ReadOnlyThreeTriosModelInterface {
  private final GameModel model;

  /**
   * Constructs the adapter.
   *
   * @param model the GameModel instance to adapt
   */
  public ModelProviderAdapter(GameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  @Override
  public CellState getCellStateAt(int row, int col) {
    // Convert CellInterface to CellState
    cs3500.tripletrios.model.CellInterface cell = model.getGameGrid().getCell(row, col);
    if (cell.isEmpty()) {
      return CellState.EMPTY;
    } else if (!cell.isCardCell()) { // Treat non-card cells as holes
      return CellState.HOLE;
    } else {
      IPlayer owner = model.getCellsPlayer(row, col);
      if (owner == null) {
        return CellState.EMPTY; // Default fallback if no owner
      }
      return owner.getColor().equals("RED") ? CellState.RED : CellState.BLUE;
    }
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return convertToProviderPlayerColor(model.getCurPlayer());
  }

  @Override
  public List<CustomCard> getCurrentPlayerHand() {
    return convertHand(model.getCurPlayer().getCurrentHand());
  }

  @Override
  public List<CustomCard> getPlayerHand(PlayerColor player) {
    IPlayer targetPlayer = convertToOurPlayerColor(player);
    return convertHand(targetPlayer.getCurrentHand());
  }

  @Override
  public Grid getGrid() {
    return grid; /// NEED TO IMPLEMENT !
  }

  @Override
  public int getScore(PlayerColor player) {
    IPlayer targetPlayer = convertToOurPlayerColor(player);
    return model.getScore(targetPlayer);
  }

  @Override
  public GameState getGameState() {
    if (!model.isGameStarted()) {
      return GameState.NOT_STARTED;
    }
    if (model.isGameOver()) {
      IPlayer winner = model.getWinner();
      if (winner == null) {
        return GameState.EARLY_QUIT;
      }
      return winner.getColor().equals("RED") ? GameState.RED_WIN : GameState.BLUE_WIN;
    }
    return GameState.IN_PROGRESS;
  }

  /**
   * Converts a list of Card to a list of CustomCard.
   *
   * @param hand the list of ICard to convert
   * @return the converted list of CustomCard
   */
  private List<CustomCard> convertHand(List<CardInterface> hand) {
    List<CustomCard> convertedHand = new ArrayList<>();
    for (CardInterface card : hand) {
      convertedHand.add(new CustomCard(///NEED TO MAKE A CUSTOMER CARD BUT IT TAKES IN A COLOR ... )
      );
    }
    return convertedHand;
  }

  /**
   * Converts your IPlayer to the provider's PlayerColor enum.
   *
   * @param player the IPlayer instance
   * @return the PlayerColor enum
   */
  private PlayerColor convertToProviderPlayerColor(IPlayer player) {
    return player.getColor().equals("RED")
            ? PlayerColor.RED
            : PlayerColor.BLUE;
  }

  /**
   * Converts the provider's PlayerColor enum to your IPlayer instance.
   *
   * @param color the provider's PlayerColor enum
   * @return the IPlayer instance
   */
  private IPlayer convertToOurPlayerColor(PlayerColor color) {
    return color == PlayerColor.RED
            ? model.getCurPlayer()
            : model.getOtherPlayer();
  }
}
