//package cs3500.tripletrios.view;
//
//import cs3500.tripletrios.controller.ControllerInterface;
//import cs3500.tripletrios.model.CardInterface;
//import cs3500.tripletrios.model.GameModel;
//import cs3500.tripletrios.model.IPlayer;
//import cs3500.tripletrios.provider.controller.GameListeners;
//import cs3500.tripletrios.provider.view.ThreeTriosGUIViewInterface;
//
//import java.io.IOException;
//
///**
// * Adapts the provider's ThreeTriosGUIViewInterface to our GameViewGUI interface.
// */
//public class ProviderViewAdapter implements GameViewGUI {
//  private final ThreeTriosGUIViewInterface providerView;
//
//  /**
//   * Constructs the adapter for integrating the provider's GUI view with our system.
//   *
//   * @param model        The shared game model in our system.
//   * @param controller   The controller managing game logic for this player.
//   * @param player       The player associated with this view.
//   * @param providerView The provider's GUI view to adapt.
//   */
//  public ProviderViewAdapter(GameModel model, ControllerInterface controller, IPlayer player,
//                             ThreeTriosGUIViewInterface providerView) {
//    if (model == null || controller == null || player == null || providerView == null) {
//      throw new IllegalArgumentException("Arguments cannot be null.");
//    }
//    this.providerView = providerView;
//
//    // Link the provider's view with a controller adapter
//    this.providerView.setController(new GameListenersAdapter(controller, player));
//  }
//
//  @Override
//  public void initializeHands() throws IOException {
//    // The provider's view does not have a direct method for initializing hands
//    providerView.render();
//  }
//
//  @Override
//  public void setVisible(boolean visible) {
//    providerView.setVisible(visible);
//  }
//
//  @Override
//  public void refreshHands() throws IOException {
//    // The provider's view refreshes the state via render
//    providerView.render();
//  }
//
//  @Override
//  public void refreshGrid() throws IOException {
//    // The provider's view refreshes the state via render
//    providerView.render();
//  }
//
//  @Override
//  public CardInterface getSelectedCard() {
//    // There is no direct mapping for selected cards, so this can return null
//    return null;
//  }
//
//  @Override
//  public void addObserver(cs3500.tripletrios.observing.Observer observer) {
//    // The provider's view does not support observers; leave empty
//  }
//
//  @Override
//  public void removeObserver(cs3500.tripletrios.observing.Observer observer) {
//    // The provider's view does not support observers; leave empty
//  }
//
//  @Override
//  public void notifyObservers() {
//    // The provider's view does not support observers; leave empty
//  }
//
//  @Override
//  public void popup(String message) {
//    providerView.displayMessage(message);
//  }
//
//  @Override
//  public void clearSelectedCard() {
//    // No direct equivalent in the provider's view; leave as no-op
//  }
//
//  @Override
//  public void setSelectedPanel(cs3500.tripletrios.view.GridPanel panel) {
//    // No direct equivalent in the provider's view; leave as no-op
//  }
//
//  @Override
//  public cs3500.tripletrios.view.GridPanel getSelectedPanel() {
//    // No direct equivalent in the provider's view; leave as null
//    return null;
//  }
//
//  @Override
//  public void clearSelectedPanel() {
//    // No direct equivalent in the provider's view; leave as no-op
//  }
//
//  @Override
//  public boolean isHandsInitialized() {
//    // Assume hands are always initialized after render
//    return true;
//  }
//
//  @Override
//  public void render() throws IOException {
//    providerView.render();
//  }
//
//  /**
//   * Adapts the controller's behavior to the GameListeners interface expected by the provider's view.
//   */
//  private static class GameListenersAdapter implements GameListeners {
//    private final ControllerInterface controller;
//
//    public GameListenersAdapter(ControllerInterface controller, IPlayer player) {
//      if (controller == null || player == null) {
//        throw new IllegalArgumentException("Arguments cannot be null.");
//      }
//      this.controller = controller;
//    }
//
//    @Override
//    public void refreshScreen() {
//      controller.update();
//    }
//
//    @Override
//    public void setScreenVisible(boolean visible) {
//      // No specific logic needed; handled by the adapter's setVisible method
//    }
//
//    @Override
//    public void runPlayerTurn() {
//      controller.update();
//    }
//
//    @Override
//    public void runGameOver() {
//      // No-op for this example; you can expand as needed
//    }
//
//    @Override
//    public void playMove(int row, int col, int handIndex) {
//      controller.playMove(row, col, handIndex);
//    }
//  }
//}
