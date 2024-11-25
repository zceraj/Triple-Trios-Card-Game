package cs3500.tripletrios.view;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.observing.ObservableInterface;
import cs3500.tripletrios.observing.Observer;

/**
 * an interface for a game view that pops up a new window to play the game.
 */
public interface GameViewGUI extends GameView, ObservableInterface {
  void initializeHands();

  /**
   * Sets the visibility of the game view GUI.
   *
   * @param visible --> true to make the GUI visible, false to hide it
   */
  void setVisible(boolean visible);

  /**
   * Refreshes the display of the player's hands in the game view.
   * This method should be called whenever the player's hand is updated.
   */
  void refreshHands();

  /**
   * Refreshes the game grid in the GUI.
   * This method should be called whenever the grid layout or state changes,
   * such as after a card is placed or the game progresses.
   */
  void refreshGrid();

  CardInterface getSelectedCard();

  // we weren't able to extend the Observable abstract class due to java rules, so we did this

  /**
   * adds an observer.
   * @param observer someone who observes
   */
  void addObserver(Observer observer);

  /**
   * removes an observer.
   * @param observer someone who observes
   */
  void removeObserver(Observer observer);

  /**
   * notifies the observers of any changes.
   */
  void notifyObservers();

  /**
   * creates a pop up message.
   * @param message what you want shown
   */
  void popup(String message);

  /**
   * resets the selected card.
   */
  void clearSelectedCard();

  void setSelectedPanel(GridPanel panel);

  GridPanel getSelectedPanel();

  void clearSelectedPanel();

  boolean isHandsInitialized();
}
