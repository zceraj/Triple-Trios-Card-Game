package cs3500.tripletrios.view;

import java.awt.*;

import cs3500.tripletrios.model.CardInterface;

public interface GridCellView {

  /**
   * Initializes the grid cell with the given card and visual style (e.g., color).
   */
  void initializeCell();

  /**
   * Highlights the grid cell when clicked (e.g., changing border color).
   */
  void highlightCell();

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

  /**
   * Custom rendering logic for the grid cell, such as displaying the card name.
   * @param g The graphics context for painting.
   */
  void renderCell(Graphics g);
}
