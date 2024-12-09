package cs3500.tripletrios.provider.controller;

import cs3500.tripletrios.controller.ThreeTriosController;
import cs3500.tripletrios.provider.model.PlayerColor;

/**
 * Meant to allow swapping between the two managers when turns are exchanged.
 */
public interface ControllerManagerInterface {

  void swapTurn(cs3500.tripletrios.model.PlayerColor playerColor);

  ThreeTriosController getController(cs3500.tripletrios.model.PlayerColor playerColor);

  /**
   * Switch from one controller and view to the other.
   *
   * @param playerColor the player to swap away from
   */
  void swapTurn(PlayerColor playerColor);

  /**
   * Get the controller of the given player.
   *
   * @param playerColor the player to get the controller of
   * @return the controller of the given player
   */
  ThreeTriosController getController(PlayerColor playerColor);
}
