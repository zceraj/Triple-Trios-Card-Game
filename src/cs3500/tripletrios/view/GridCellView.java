package cs3500.tripletrios.view;

import cs3500.tripletrios.model.CardInterface;

/**
 * Represents a view component for a single cell in the game grid of the "Triple Trio" game.
 * This interface defines the methods needed to display and interact with an individual cell
 * in the GUI, allowing for flexible rendering and event handling for each cell.
 */
public interface GridCellView {

  /**
   * Returns the card that is placed in this grid cell.
   * @return The card in this grid cell, or null if no card is placed.
   */
  CardInterface getCard();

  /**
   * Handles a click event on this grid cell, such as selecting the card or triggering a move.
   */
  void handleGridCellClick();

  /**
   * Returns the row index of this grid cell.
   * @return The row index.
   */
  int getRow();

  /**
   * Returns the column index of this grid cell.
   * @return The column index.
   */
  int getCol();
}
