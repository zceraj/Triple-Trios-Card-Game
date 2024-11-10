package cs3500.tripletrios.view;

import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.ReadOnlyGameModel;

public interface GameViewGUI extends GameView {
  /**
   * Initializes the grid panel based on the game's state.
   * This method is responsible for rendering the game board grid.
   */
  void initializeGrid();

  /**
   * Initializes the player hands and updates the UI to reflect the cards in the players' hands.
   */
  void initializeHands();

  /**
   * Highlights the selected card in the player’s hand.
   * @param card The card to be highlighted.
   */
  void highlightSelectedCard(Card card);

  /**
   * Updates the UI to reflect changes in the game, such as moving a card to the grid.
   */
  void updateGame();

  /**
   * Handles the logic for when a card is clicked.
   * @param card The card that was clicked.
   */
  void handleCardClick(Card card);

  /**
   * Handles the logic for when a grid cell is clicked.
   * @param row The row index of the clicked grid cell.
   * @param col The column index of the clicked grid cell.
   */
  void handleGridClick(int row, int col);

  /**
   * Makes the view visible or invisible.
   * @param visible True to make the view visible, false to hide it.
   */
  void setVisible(boolean visible);

  /**
   * Displays an error message in the view.
   * @param message The message to be displayed.
   */
  void displayErrorMessage(String message);

  /**
   * Updates the player's hand on the UI after a card is played.
   * @param cards The updated list of cards in the player's hand.
   */
  void updatePlayerHand(List<Card> cards);
}
