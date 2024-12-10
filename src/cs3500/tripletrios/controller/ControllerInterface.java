package cs3500.tripletrios.controller;

import cs3500.tripletrios.observing.Observer;

/**
 * Represents the controller for the Triple Trio game.
 * It mediates interactions between the view and the model,
 * handling user inputs and updating the game state accordingly.
 */
public interface ControllerInterface extends Observer {
  // the only method called in the controller is from observer and it is the update method

  /**
   * Plays the card at the given index in the player's hand to the given coordinates.
   * Added it for the adaptor for the providers code.
   *
   * @param row       the row of the position to play
   * @param col       the column of the position to play
   * @param handIndex the index in hand of the card to be played
   */
  public void playMove(int row, int col, int handIndex);
}
