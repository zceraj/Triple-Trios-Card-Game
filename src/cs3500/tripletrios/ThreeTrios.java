package cs3500.tripletrios;

import java.io.File;

import cs3500.tripletrios.controller.SetUp;
import cs3500.tripletrios.controller.ThreeTriosController;
import cs3500.tripletrios.model.AiPlayer;
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
    // setting up the grid and cards using the card and grid file readers
    String cardFilePath =  "."
            + File.separator
            + "TESTINGFILES"
            + File.separator
            + "test_cards.txt";
    String gridFilePath = "."
            + File.separator
            + "TESTINGFILES"
            + File.separator
            + "valid_grid.text";

    SetUp setup = new SetUp(cardFilePath, gridFilePath);


    // implement the strategy pattern here
    IPlayer player1 = new HumanPlayer();
    IPlayer player2 = new HumanPlayer();
    IPlayer player3 = new AiPlayer();
    IPlayer player4 = new AiPlayer();

    GameModelImpl model = new GameModelImpl(setup.setGrid(), player1, player2);
    GameViewGUI viewPlayer1 = new TripleTrioGuiView(model);
    GameViewGUI viewPlayer2 = new TripleTrioGuiView(model);
    ThreeTriosController controller1 = new ThreeTriosController(model, model.getCurPlayer(), viewPlayer1);
    ThreeTriosController controller2 = new ThreeTriosController(model, model.getOtherPlayer(), viewPlayer2);
    model.startGame(setup.setCards());
  }
}