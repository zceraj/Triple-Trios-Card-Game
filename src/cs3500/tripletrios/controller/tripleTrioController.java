import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.tripletrios.model.GameModelImpl;

public class TripleTrioController {
  private final GameModelImpl model; // The mutable game model
  private final TripleTrioGuiView view;   // The view interface to display the game

  public TripleTrioController(GameModelImpl model, TripleTriosView view) {
    this.model = model;
    this.view = view;

    // Set up event listeners for buttons in the view
    view.addRestartButtonListener(new RestartButtonListener());
    view.addGridClickListener(new GridClickListener());

    startNewGame();
  }

  // Starts a new game by resetting the model and updating the view
  private void startNewGame() {
    model.startGame();
    view.resetGrid(model.getGridRows(), model.getGridCols());
    updateView();
  }

  // Updates the view with the latest game state
  private void updateView() {
    view.updateGrid(model.getGridState());
    view.updatePlayerInfo(model.getCurrentPlayerInfo());
    view.updateScore(model.getPlayerScores());
    if (model.checkGameOver()) {
      view.displayGameOver(model.getWinner());
    }
  }

  // Listener for the "Restart" button in the view
  private class RestartButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      startNewGame();
    }
  }

  // Listener for clicks on the game grid
  private class GridClickListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      // Calculate which grid cell was clicked
      int row = view.getRowFromClick(e.getY());
      int col = view.getColFromClick(e.getX());

      if (model.isValidMove(row, col)) {
        // Place the current player's card at the clicked position
        model.placeCard(row, col);
        updateView();

        if (model.checkGameOver()) {
          view.displayGameOver(model.getWinner());
        } else {
          model.switchToNextPlayer();
          updateView();
        }
      } else {
        view.showInvalidMoveMessage();
      }
    }
  }
}

