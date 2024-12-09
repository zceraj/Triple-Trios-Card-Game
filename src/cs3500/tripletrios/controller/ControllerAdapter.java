package cs3500.tripletrios.controller;

import java.io.IOException;

import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.provider.controller.ControllerManagerInterface;
import cs3500.tripletrios.provider.controller.GameListeners;

/**
 * Adapter that bridges the GameListeners and ControllerManagerInterface
 * to the ThreeTriosController.
 */
public final class ControllerAdapter implements GameListeners, ControllerManagerInterface {
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
  public void refreshScreen() throws IOException {
    ThreeTriosController currentController = getCurrentController();
    currentController.view.refreshGrid();
    currentController.view.refreshHands();
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
    ThreeTriosController currentController = getCurrentController();
    currentController.view.popup("Game Over! " + currentController.model.getWinner() + " wins!");
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
    switch (playerColor) {
      case RED:
        return redController;
      case BLUE:
        return blueController;
      default:
        throw new IllegalArgumentException("Invalid player color.");
    }
  }



  @Override
  public void swapTurn(cs3500.tripletrios.provider.model.PlayerColor playerColor) {
    swapTurn(convertToOurColor(playerColor));
  }

  @Override
  public ThreeTriosController getController(cs3500.tripletrios.provider.model.PlayerColor playerColor) {
    return getController(convertToOurColor(playerColor));
  }

  /**
   * Determines the current controller based on the active player.
   *
   * @return the current player's controller
   */
  private ThreeTriosController getCurrentController() {
    if (redController.model.getCurPlayer() == redController.player) {
      return redController;
    } else if (blueController.model.getCurPlayer() == blueController.player) {
      return blueController;
    } else {
      throw new IllegalStateException("No matching controller for the current player.");
    }
  }

  /**
   * Converts the provider's PlayerColor enum to our PlayerColor enum.
   *
   * @param color the provider's PlayerColor
   * @return the corresponding PlayerColor in our system
   */
  private PlayerColor convertToOurColor(cs3500.tripletrios.provider.model.PlayerColor color) {
    switch (color) {
      case RED:
        return PlayerColor.RED;
      case BLUE:
        return PlayerColor.BLUE;
      default:
        throw new IllegalArgumentException("Invalid color: " + color);
    }
  }

}
