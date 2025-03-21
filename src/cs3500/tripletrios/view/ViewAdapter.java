package cs3500.tripletrios.view;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.observing.Observer;
import cs3500.tripletrios.provider.view.ThreeTriosGUIView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Adapter to integrate the ThreeTriosGUIViewInterface with the existing GUI structure.
 */
public class ViewAdapter extends JFrame implements GameViewGUI {
  private final List<Observer> observers;
  private final ThreeTriosGUIView view;

  private CardInterface selectedCard;
  private GridPanel selectedPanel;
  private boolean handsInitialized;

  /**
   * Constructs a new adapter for the Three Trios GUI view.
   *
   * @param model  the read-only model of the game.
   */
  public ViewAdapter(GameModel model, ThreeTriosGUIView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and player cannot be null");
    }
    this.view = view;
    this.handsInitialized = false;
    this.observers = new ArrayList<>();
  }

  @Override
  public void initializeHands() {
    view.render();
    view.setVisible(true);
    repaint();
    this.handsInitialized = true;
  }

  @Override
  public void refreshHands() throws IOException {
    view.render();
  }

  @Override
  public void refreshGrid() throws IOException {
    view.render();
  }

  @Override
  public CardInterface getSelectedCard() {
    return selectedCard;
  }

  @Override
  public void addObserver(Observer observer) {
    this.observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    this.observers.remove(observer);
  }

  @Override
  public void notifyObservers() throws IOException {
    for (Observer observer : this.observers) {
      observer.update();
    }
  }

  @Override
  public void popup(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  @Override
  public void clearSelectedCard() throws IOException {
    this.selectedCard = null;
  }

  @Override
  public void setSelectedPanel(GridPanel panel) {
    this.selectedPanel = panel;
  }

  @Override
  public GridPanel getSelectedPanel() {
    return this.selectedPanel;
  }

  @Override
  public void clearSelectedPanel() {
    this.selectedPanel = null;
  }

  @Override
  public boolean isHandsInitialized() {
    return handsInitialized;
  }


  @Override
  public void render() throws IOException {
    view.render();
  }
}
