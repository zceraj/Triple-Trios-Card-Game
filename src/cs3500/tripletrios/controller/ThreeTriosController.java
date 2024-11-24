package cs3500.tripletrios.controller;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.view.GameViewGUI;

/**
 * Controls the flow of the Three Trios game.
 */
public class ThreeTriosController implements ControllerInterface {
  private final GameModel model;
  private final GameViewGUI view;

  /**
   * Constructs a controller for managing the game for a specific player.
   *
   * @param model  the game model shared across all players.
   * @param view   the view associated with this player.
   */
  public ThreeTriosController(GameModel model, GameViewGUI view) {
    if (model == null || view == null) {
      view.popup("model or view cannot be null");
      throw new IllegalArgumentException("model or view cannot be null");
    }
    else {
      this.model = model;
      model.addObserver(this);
      this.view = view;
      this.view.addObserver(this);
      view.setVisible(true);
      view.refreshHands();
      view.refreshGrid();
    }
  }

  /**
   * Handles the event when a cell in the grid is clicked by the player.
   *
   * @param row the row of the clicked cell.
   * @param col the column of the clicked cell.
   */
  @Override
  public void handleGridClick(int row, int col) {
    while (canMove()) {
      CardInterface selectedCard = view.getSelectedCard();
      if (selectedCard == null) {
        view.popup("No card selected. Please select a card first.");
        return;
      }

      try {
        model.placeCard(selectedCard, row, col);
        model.battles(row, col);

        // Refresh the grid and hands after the card placement
        view.refreshGrid();
        view.refreshHands();

        // Notify the model to advance the turn
        model.nextTurn();
      } catch (IllegalArgumentException | IllegalStateException e) {
        System.err.println("Failed to place card: " + e.getMessage());
      }
    }
    System.out.println("Hey girl, it is not your turn:( please wait!");
  }


  @Override
  public void update() {
    // Check if the game is over
    if (model.isGameOver()) {
      view.popup("Game Over! " + model.getWinner() + " wins!");
      return;
    }

    view.refreshHands();
    view.refreshGrid();

    if (view.getSelectedCard() != null && view.getSelectedPanel() != null) {
      CardInterface card = view.getSelectedCard();
      Cell cell = view.getSelectedPanel();

      if (isValidMove(card, cell)) {
        model.placeCard(card, cell.getRow(), cell.getCol());
        model.nextTurn();
        view.clearSelectedCard();
        view.refreshHands();
        view.refreshGrid();
      } else {
        view.popup("Invalid move!");
      }
    }

  }

  /**
   * Checks if the current player is allowed to move.
   *
   * @return true if the current player is allowed to move; false otherwise
   */
  private boolean canMove() {
    return model.getCurPlayer().equals(model.getCurPlayer());
  }

  private boolean isValidMove(CardInterface card, Cell cell) {
    return (cell.isCardCell() && cell.isEmpty());
  }
}
