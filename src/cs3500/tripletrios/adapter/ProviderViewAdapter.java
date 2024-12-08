package cs3500.tripletrios.adapter;

import java.io.IOException;

import cs3500.tripletrios.controller.ControllerInterface;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.observing.Observer;
import cs3500.tripletrios.provider.controller.GameListeners;
import cs3500.tripletrios.provider.view.BoardPanelInterface;
import cs3500.tripletrios.provider.view.HandPanelInterface;
import cs3500.tripletrios.provider.view.ThreeTriosGUIViewInterface;
import cs3500.tripletrios.view.GameViewGUI;
import cs3500.tripletrios.view.GridPanel;
import cs3500.tripletrios.view.TripleTrioGuiView;

/**
 * Adapter to integrate the provider's view interfaces into our system.
 */
public class ProviderViewAdapter implements GameViewGUI {
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
   */
  public ProviderViewAdapter(GameModel model, ControllerInterface controller, IPlayer player,
                             ThreeTriosGUIViewInterface providerView) {
    this.model = model;
    this.controller = controller;
    this.player = player;
    this.providerView = providerView;

    // Set up the provider's controller
    this.providerView.setController(new ControllerAdapter(controller));
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

  @Override
  public void initializeHands() {

  }

  @Override
  public void setVisible(boolean visible) {

  }

  @Override
  public void refreshHands() {

  }

  @Override
  public void refreshGrid() {

  }

  @Override
  public CardInterface getSelectedCard() {
    return null;
  }

  @Override
  public void addObserver(Observer observer) {

  }

  @Override
  public void removeObserver(Observer observer) {

  }

  @Override
  public void notifyObservers() {

  }

  @Override
  public void popup(String message) {

  }

  @Override
  public void clearSelectedCard() {

  }

  @Override
  public void setSelectedPanel(GridPanel panel) {

  }

  @Override
  public GridPanel getSelectedPanel() {
    return null;
  }

  @Override
  public void clearSelectedPanel() {

  }

  @Override
  public boolean isHandsInitialized() {
    return false;
  }

  @Override
  public void render() throws IOException {

  }
}
