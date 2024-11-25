package cs3500.tripletrios.controller;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.view.GameViewGUI;
import cs3500.tripletrios.view.GridPanel;

/**
 * Controls the flow of the Three Trios game.
 */
public class ThreeTriosController implements ControllerInterface {
  private final GameModel model;
  private final GameViewGUI view;
  private final IPlayer player;
  private boolean justStarted;

  /**
   * Constructs a controller for managing the game for a specific player.
   *
   * @param model the game model shared across all players.
   * @param view  the view associated with this player.
   */
  public ThreeTriosController(GameModel model, GameViewGUI view, IPlayer player) {
    if (model == null || view == null || player == null) {
      view.popup("model, player, or view cannot be null");
      throw new IllegalArgumentException("model, player, or view cannot be null");
    } else {
      this.model = model;
      model.addObserver(this);
      this.view = view;
      this.player = player;
      this.view.addObserver(this);
      view.setVisible(true);
      view.refreshGrid();
      model.notifyObservers();
    }
  }



  @Override
  public void update() {
    gameOver();
    view.initializeHands();
    view.refreshGrid();
    gameOver();

    if (this.player != model.getCurPlayer()) {
      view.popup("waiting for " + model.getCurPlayer().getName() + "'s turn");
    }

    if (view.getSelectedCard() != null && view.getSelectedPanel() != null) {
      if (!isValidMove(
              view.getSelectedCard(),
              model.getGameGrid().getCell(
                      view.getSelectedPanel().getRow(),
                      view.getSelectedPanel().getCol()))){
        view.popup("invalid move");
      }
      else {
        CardInterface card = view.getSelectedCard();
        GridPanel panel = view.getSelectedPanel();
        model.placeCard(card, panel.getRow(), panel.getCol());
        model.nextTurn();
        view.refreshHands();
        view.refreshGrid();
        view.clearSelectedPanel();
        view.clearSelectedCard();
        gameOver();
      }
    }
    if (view.getSelectedPanel() != null) {
      view.popup("please select card first");
      view.clearSelectedPanel();
    }
  }

  public void gameOver(){
    // Recheck game over after updating the model
    if (model.isGameOver()) {
      view.popup("Game Over! " + model.getWinner() + " wins!");
    }
  }

  private boolean isValidMove(CardInterface card, Cell cell) {
    return (cell.isCardCell() && cell.isEmpty());
  }
}
