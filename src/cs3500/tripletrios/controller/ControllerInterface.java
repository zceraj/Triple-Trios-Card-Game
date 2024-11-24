package cs3500.tripletrios.controller;

import cs3500.tripletrios.observing.ObservableInterface;
import cs3500.tripletrios.observing.Observer;

/**
 * Represents the controller for the Triple Trio game.
 * It mediates interactions between the view and the model,
 * handling user inputs and updating the game state accordingly.
 */
public interface ControllerInterface extends Observer {
  /**
   * handles when a cell is clicked and update is called.
   *
   * @param row  of the cell
   * @param col of the cell
   */
  void handleGridClick(int row, int col);
}
