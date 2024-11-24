package cs3500.tripletrios.controller;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.observing.Observer;
import cs3500.tripletrios.view.GameViewGUI;

public class ThreeTriosController implements ControllerInterface, Observer {
  private final GameModel model;
  private final IPlayer player;
  private final GameViewGUI view;

  /**
   * Constructs a controller for managing the game for a specific player.
   *
   * @param model  the game model shared across all players.
   * @param player the player managed by this controller.
   * @param view   the view associated with this player.
   */
  public ThreeTriosController(GameModel model, IPlayer player, GameViewGUI view) {
    if (model == null || player == null || view == null) {
      view.popup("model, null, or view cannot be null");
      throw new IllegalArgumentException("model, player, or view cannot be null");
    }
    else {
      this.model = model;
      model.addObserver(this);
      this.player = player;
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

//    // Check whose turn it is and update the view accordingly
//    if (model.getCurPlayer() == player) {
//      view.popup("It's your turn, " + player.getName() + ". Select a card to play!");
//    } else {
//      view.popup("Waiting for " + model.getCurPlayer().getName() + "'s move...");
//    }
//
//    if (view.getSelectedCard() != null) {
//      System.out.println("Selected card: " + view.getSelectedCard() + "select cell");
//      if (view.getSelectedPanel() != null) {
//        handleGridClick(view.getSelectedPanel().getRow(), view.getSelectedPanel().getCol());
//
//        // Update the view to reflect the latest game state
//        view.refreshHands();
//        view.refreshGrid();
//        view.clearSelectedCard();
//      }
    if (view.getSelectedCard() != null && view.getSelectedPanel() != null) {
      CardInterface card = view.getSelectedCard();
      Cell cell = view.getSelectedPanel();

      if (isValidMove(card, cell)) {
        model.placeCard(card, cell.getRow(), cell.getCol());
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
    if (cell.isCardCell() && cell.isEmpty()) {
      return true;
    }
    return false;
  }
}
