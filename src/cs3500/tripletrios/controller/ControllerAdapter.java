package cs3500.tripletrios.controller;

import cs3500.tripletrios.controller.ThreeTriosController;
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.provider.controller.ControllerManagerInterface;
import cs3500.tripletrios.provider.controller.GameListeners;

/**
 * Adapter that bridges the GameListeners and ControllerManagerInterface
 * to the ThreeTriosController.
 */
public class ControllerAdapter implements GameListeners, ControllerManagerInterface {
  private final ThreeTriosController redController;
  private final ThreeTriosController blueController;

  /**
   * Constructs the adapter with the controllers for each player.
   *
   * @param redController the controller for the red player
   * @param blueController the controller for the blue player
   */
  public ControllerAdapter(ThreeTriosController redController, ThreeTriosController blueController) {
    if (redController == null || blueController == null) {
      throw new IllegalArgumentException("Controllers cannot be null.");
    }
    this.redController = redController;
    this.blueController = blueController;
  }

  @Override
  public void refreshScreen() {
    getCurrentController().view.refreshGrid();
    getCurrentController().view.refreshHands();
  }

  @Override
  public void setScreenVisible(boolean visible) {
    getCurrentController().view.setVisible(visible);
  }

  @Override
  public void runPlayerTurn() {
    getCurrentController().update();
  }

  @Override
  public void runGameOver() {
    getCurrentController().view.popup("Game Over! " + getCurrentController().model.getWinner() + " wins!");
  }

  @Override
  public void playMove(int row, int col, int handIndex) {
    getCurrentController().playMove(row, col, handIndex);
  }

  @Override
  public void swapTurn(PlayerColor playerColor) {
    if (playerColor == PlayerColor.RED) {
      redController.model.nextTurn();
    } else if (playerColor == PlayerColor.BLUE) {
      blueController.model.nextTurn();
    } else {
      throw new IllegalArgumentException("Invalid player color.");
    }
  }

  @Override
  public ThreeTriosController getController(PlayerColor playerColor) {
    if (playerColor == PlayerColor.RED) {
      return redController;
    } else if (playerColor == PlayerColor.BLUE) {
      return blueController;
    } else {
      throw new IllegalArgumentException("Invalid player color.");
    }
  }

  /**
   * Determines the current controller based on the active player.
   *
   * @return the current player's controller
   */
  private ThreeTriosController getCurrentController() {
    if (redController.model.getCurPlayer() == redController.player) {
      return redController;
    } else {
      return blueController;
    }
  }

  @Override
  public void swapTurn(cs3500.tripletrios.provider.model.PlayerColor playerColor) {

  }

  @Override
  public ThreeTriosController getController(cs3500.tripletrios.provider.model.PlayerColor playerColor) {
    return null;
  }
}
