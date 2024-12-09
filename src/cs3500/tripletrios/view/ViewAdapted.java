
package cs3500.tripletrios.view;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.observing.Observer;
import cs3500.tripletrios.provider.controller.GameListeners;
import cs3500.tripletrios.provider.controller.players.Player;
import cs3500.tripletrios.provider.model.ReadOnlyThreeTriosModelInterface;
import cs3500.tripletrios.provider.model.PlayerColor;
import cs3500.tripletrios.provider.view.ThreeTriosGUIView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to adapt the provider's ThreeTriosGUIView to our GameViewGUI interface.
 */
public class ViewAdapted implements GameViewGUI {
  private final ThreeTriosGUIView providerView;
  private final List<Observer> observers;
  private CardInterface selectedCard;
  private GridPanel selectedPanel;
  private boolean handsInitialized;

  /**
   * Constructs the adapter.
   *
   * @param model  the model for the provider's view
   * @param player the player associated with this view
   */
  public ViewAdapted(ReadOnlyThreeTriosModelInterface model, Player player) {
    this.providerView = new ThreeTriosGUIView(model, player);
    this.observers = new ArrayList<>();
    this.handsInitialized = false;
  }

  @Override
  public void initializeHands() throws IOException {
    // Logic for initializing hands (if possible, based on the provider's view)
    this.handsInitialized = true;
  }

  @Override
  public void setVisible(boolean visible) {
    providerView.setVisible(visible);
  }

  @Override
  public void refreshHands() throws IOException {

  }

  @Override
  public void refreshGrid() throws IOException {

  }

  @Override
  public CardInterface getSelectedCard() {
    return selectedCard;
  }

  @Override
  public void clearSelectedCard() {
    selectedCard = null;
  }

  @Override
  public void setSelectedPanel(GridPanel panel) {
    this.selectedPanel = panel;
  }

  @Override
  public GridPanel getSelectedPanel() {
    return selectedPanel;
  }

  @Override
  public void clearSelectedPanel() {
    selectedPanel = null;
  }

  @Override
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }

  @Override
  public void popup(String message) {
    providerView.displayMessage(message);
  }

  @Override
  public boolean isHandsInitialized() {
    return handsInitialized;
  }

  @Override
  public void render() throws IOException {

  }
}
