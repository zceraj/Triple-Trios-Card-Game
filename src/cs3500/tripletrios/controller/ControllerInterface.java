package cs3500.tripletrios.controller;

import cs3500.tripletrios.observing.Observer;

/**
 * Represents the controller for the Triple Trio game.
 * It mediates interactions between the view and the model,
 * handling user inputs and updating the game state accordingly.
 */
public interface ControllerInterface extends Observer {
  // the only method called in the controller is from observer and it is the update method
}
