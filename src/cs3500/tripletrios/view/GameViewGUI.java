package cs3500.tripletrios.view;

import java.util.function.BiConsumer;

import cs3500.tripletrios.model.CardInterface;

/**
 * an interface for a game view that pops up a new window to play the game.
 */
public interface GameViewGUI extends GameView {
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

  void addCardClickListener(BiConsumer<CardInterface, Integer> listener);
  void addGridClickListener(BiConsumer<Integer, Integer> listener);
}
