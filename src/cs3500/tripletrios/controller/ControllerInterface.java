package cs3500.tripletrios.controller;

import java.io.IOException;

import cs3500.tripletrios.model.CardInterface;

/**
 * Represents the controller for the Triple Trio game.
 * It mediates interactions between the view and the model,
 * handling user inputs and updating the game state accordingly.
 */
public interface ControllerInterface {
  /**
   * Starts the game using the provided configuration.
   * Initializes the view and updates the model state.
   */
  void startGame();


  void setupViewListeners();

  void handleCardClick(CardInterface card, int cardIndex);

  void handleGridClick(int row, int col);
}
