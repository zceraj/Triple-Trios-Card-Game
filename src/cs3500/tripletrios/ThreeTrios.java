package cs3500.tripletrios;

import java.io.File;

import cs3500.tripletrios.controller.SetUp;
import cs3500.tripletrios.controller.ThreeTriosController;
import cs3500.tripletrios.model.AiPlayer;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.strategy.ChainStrategy;
import cs3500.tripletrios.strategy.StrategyInterface;
import cs3500.tripletrios.strategy.StrategyOne;
import cs3500.tripletrios.strategy.StrategyTwo;
import cs3500.tripletrios.view.GameViewGUI;
import cs3500.tripletrios.view.TripleTrioGuiView;


/**
 * the entry point for running the Triple Trios game.
 * It initializes the game model, players, grid configuration, and cards,
 * then launches the GUI view.
 */

public final class ThreeTrios {


  private static IPlayer dispatch(String arg){
    IPlayer player;
    String playerStrat;

    switch (arg) {
      case "AI":
      case "AIPlayer":
        player = new AiPlayer("player1", PlayerColor.RED);
      case "Strategy One":
        playerStrat = "Strategy One";
        break;
      case "Strategy Two":
        playerStrat = "Strategy Two";
        break;
      case "Strategy Three":
        playerStrat = "Strategy Three";
        break;
      case "Strategy Four":
        playerStrat = "Strategy Four";
        break;
      case "Human":
      case "HumanPlayer":
        player = new HumanPlayer("player1", PlayerColor.RED);
        break;
      default:
        throw new IllegalArgumentException("Unknown player or strategy: " + arg);
    }

  }
/**
   * The main method that sets up the Triple Trios game. It creates two players,
   * a grid layout, a list of cards, initializes the game model, and launches the GUI.
   *
   * @param args command-line arguments (not used) --> it won't let me run it without it??
   */

  public static void main(String[] args) {
    IPlayer player1;
    IPlayer player2;
    String player1Strat;
    String player2Strat;

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

    if (args.length == 0) {
      System.out.println("No arguments provided. Game will be started with 2 human players");
      player1 = new HumanPlayer("player1", PlayerColor.RED);
      player2 = new HumanPlayer("player2", PlayerColor.BLUE);
      player1Strat = null;
      player2Strat = null;
      return;


    GameModelImpl model = new GameModelImpl(setup.setGrid(), player1, player2);


    GameViewGUI viewPlayer1 = new TripleTrioGuiView(model);
    GameViewGUI viewPlayer2 = new TripleTrioGuiView(model);
    ThreeTriosController controller1 = new ThreeTriosController(model, model.getCurPlayer(), viewPlayer1);
    ThreeTriosController controller2 = new ThreeTriosController(model, model.getOtherPlayer(), viewPlayer2);
    model.startGame(setup.setCards());
  }
}
