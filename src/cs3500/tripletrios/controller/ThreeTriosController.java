package cs3500.tripletrios.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.view.GameViewGUI;

public class ThreeTriosController implements ControllerInterface, ActionListener {
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
    this.model = model;
    this.player = player;
    this.view = view;
  }

  /**
   * Starts the game for this controller, ensuring the view is set up
   * and listening to player actions.
   */
  @Override
  public void startGame() {
    try {
      view.render();
    } catch (IOException e) {
      System.err.println("Failed to render the view: " + e.getMessage());
    }

    // Refresh the view to reflect the model's current state
    view.refreshHands();
    view.refreshGrid();

    // Add event listeners to the view (if applicable)
    setupViewListeners();
  }

  /**
   * Sets up event listeners for the view to handle user interactions.
   */
  @Override
  public void setupViewListeners() {
    view.addCardClickListener((card, cardIndex) -> handleCardClick(card, cardIndex));
    view.addGridClickListener((row, col) -> handleGridClick(row, col));
  }

  /**
   * Handles the event when a card is clicked by the player in their hand.
   *
   * @param card      the card clicked by the player.
   * @param cardIndex the index of the card in the player's hand.
   */
  @Override
  public void handleCardClick(CardInterface card, int cardIndex) {
    while (canMove()) {
      if (!player.getHand().contains(card)) {
        System.out.println("Invalid card selection: Card not in player's hand.");
        return;
      }

      System.out.println("Player " + player.getName() + " selected card: " + card.getCardName());
      view.refreshHands();
    }
    System.out.println("hey girl, it is not your turn. please wait!");
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
        System.out.println("No card selected. Please select a card first.");
        return;
      }

      try {
        model.placeCard(selectedCard, row, col);
        model.battles(row, col);

        System.out.println("Player " + player.getName() + " placed card at (" + row + ", " + col + ").");

        // Refresh the grid and hands after the card placement
        view.refreshGrid();
        view.refreshHands();

        // Notify the model to advance the turn
        model.nextTurn();
      } catch (IllegalArgumentException | IllegalStateException e) {
        System.err.println("Failed to place card: " + e.getMessage());
      }
    }
    System.out.println("hey girl, it is not your turn. please wait!");
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  private boolean canMove() {
    if (model.getCurPlayer() == player) {
      return true;
    } else {
      return false;
    }
  }

}
