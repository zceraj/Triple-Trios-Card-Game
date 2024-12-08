package cs3500.tripletrios.provider.adapter;

import cs3500.tripletrios.controller.ControllerInterface;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.provider.controller.GameListeners;
import cs3500.tripletrios.provider.view.BoardPanelInterface;
import cs3500.tripletrios.provider.view.HandPanelInterface;
import cs3500.tripletrios.provider.view.ThreeTriosGUIViewInterface;

/**
 * Adapter to integrate the provider's view interfaces into our system.
 */
public class ProviderViewAdapter implements GameListeners {
  private final GameModel model;
  private final ControllerInterface controller;
  private final IPlayer player;
  private final ThreeTriosGUIViewInterface providerView;
  private final BoardPanelInterface boardPanel;
  private final HandPanelInterface handPanel;

  /**
   * Constructs the adapter.
   *
   * @param model       The shared game model.
   * @param controller  Our controller to manage game logic.
   * @param player      The player associated with the provider's view.
   * @param providerView The provider's main GUI view.
   * @param boardPanel   The provider's board panel.
   * @param handPanel    The provider's hand panel.
   */
  public ProviderViewAdapter(GameModel model, ControllerInterface controller, IPlayer player,
                             ThreeTriosGUIViewInterface providerView,
                             BoardPanelInterface boardPanel,
                             HandPanelInterface handPanel) {
    this.model = model;
    this.controller = controller;
    this.player = player;
    this.providerView = providerView;
    this.boardPanel = boardPanel;
    this.handPanel = handPanel;

    // Set up the provider's controller
    this.providerView.setController(this);
    this.boardPanel.setController(this);
  }

  @Override
  public void refreshScreen() {
    providerView.setVisible(true);
  }

  @Override
  public void setScreenVisible(boolean visible) {
    providerView.setVisible(visible);
  }

  @Override
  public void runPlayerTurn() {
    if (model.getCurPlayer() == player) {
      controller.update();
    } else {
      providerView.displayMessage("Waiting for " + model.getCurPlayer().getName() + "'s turn.");
    }
  }

  @Override
  public void runGameOver() {
    if (model.isGameOver()) {
      providerView.displayMessage("Game Over! " + model.getWinner() + " wins!");
    }
  }

  @Override
  public void playMove(int row, int col, int handIndex) {
    if (model.getCurPlayer() == player) {
      controller.playMove(row, col, handIndex);
    } else {
      providerView.displayMessage("It's not your turn!");
    }
  }

  public void handleCellClick(int row, int col) {
    if (model.getCurPlayer() == player) {
      playMove(row, col, handPanel.getSelectedIndex());
    }
  }

  public void handleCardClick(int handIndex) {
    if (model.getCurPlayer() == player) {
      // Notify the controller of the card click
      handPanel.handleCardClick(handIndex);
    } else {
      providerView.displayMessage("It's not your turn!");
    }
  }
}
