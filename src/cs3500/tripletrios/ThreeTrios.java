package cs3500.tripletrios;

import cs3500.tripletrios.controller.ThreeTriosController;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.view.GameViewGUI;
import cs3500.tripletrios.view.TripleTrioGuiView;

/**
 * the entry point for running the Triple Trios game.
 * It initializes the game model, players, grid configuration, and cards,
 * then launches the GUI view.
 */
public final class ThreeTrios {
  /**
   * The main method that sets up the Triple Trios game. It creates two players,
   * a grid layout, a list of cards, initializes the game model, and launches the GUI.
   *
   * @param args command-line arguments (not used) --> it won't let me run it without it??
   */
  public static void main(String[] args) {
    GameModelImpl model = new GameModelImpl();
    GameViewGUI viewPlayer1 = new TripleTrioGuiView(model);
    GameViewGUI viewPlayer2 = new TripleTrioGuiView(model);
    IPlayer player1 = new HumanPlayer();
    IPlayer player2 = new HumanPlayer();
    ThreeTriosController controller1 = new ThreeTriosController(model, player1, viewPlayer1);
    ThreeTriosController controller2 = new ThreeTriosController(model, player2, viewPlayer2);
    model.startGame();
  }
}