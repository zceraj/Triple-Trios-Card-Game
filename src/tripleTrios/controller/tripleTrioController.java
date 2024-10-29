package tripleTrios.controller;

import tripleTrios.model.GameModel;
import tripleTrios.model.GameModelImpl;
import tripleTrios.view.GameView;

public class tripleTrioController {
  private GameView view;
  private GameModel model;

  /**
   * the constructor for the controller.
   * @param view sets the view that the controller will be using.
   * @param model sets the model that the controller will be using.
   */
  public tripleTrioController(GameView view, GameModel model) {
    this.view = view;
    this.model = model;
  }

  /**
   * method to start the game that calls the helper
   * fuctions which use view and model to execute the commands.
   */
  public void startGame() {
    model.initializeGame();
    view.updateGrid(model.getGrid()); // Update the view with the initial grid
    view.setCurrentPlayer(model.getCurrentPlayer());
  }

  public void playTurn(int row, int col, int cardIndex) {
  }

  public void displayGameState() {
  }




    // Method to handle cell clicks
    public void handleCellClick(int row, int col) {
      if (model.isMoveLegal(row, col)) {
        model.placeCard(row, col); // Update model state
        view.updateGrid(model.getGrid()); // Refresh the view
        view.appendLog("Player " + model.getCurrentPlayer() + " placed a card at (" + row + ", " + col + ")");

        // Check for battle and update view accordingly
        model.battlePhase(row, col);
        view.updateGrid(model.getGrid()); // Refresh the view after battle
        switchPlayer(); // Switch to the other player
      } else {
        view.appendLog("Illegal move by player " + model.getCurrentPlayer());
      }
    }

    // Method to switch players
    private void switchPlayer() {
      model.switchPlayer();
      view.setCurrentPlayer(model.getCurrentPlayer());
    }
  }

}
