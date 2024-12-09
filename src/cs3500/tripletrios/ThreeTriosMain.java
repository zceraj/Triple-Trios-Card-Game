package cs3500.tripletrios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.tripletrios.controller.SetUp;
import cs3500.tripletrios.controller.ThreeTriosController;
import cs3500.tripletrios.model.AiPlayer;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ModelProviderAdapter;
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.strategy.StrategyInterface;
import cs3500.tripletrios.strategy.StrategyOne;
import cs3500.tripletrios.strategy.StrategyThree;
import cs3500.tripletrios.strategy.StrategyTwo;
import cs3500.tripletrios.strategy.StrategyFour;
import cs3500.tripletrios.strategy.ChainStrategy;
import cs3500.tripletrios.view.GameViewGUI;
import cs3500.tripletrios.view.TripleTrioGuiView;
import cs3500.tripletrios.view.ViewAdapted;

/**
 * the entry point for running the Triple Trios game.
 * It initializes the game model, players, grid configuration, and cards,
 * then launches the GUI view.
 */

public final class ThreeTriosMain {

  private static List<IPlayer> creatingPlayers(String[] args) {
    IPlayer player1 = null;
    IPlayer player2 = null;

    player1 = new HumanPlayer("player1", PlayerColor.RED);
    player2 = new HumanPlayer("player2", PlayerColor.BLUE);

    return List.of(player1, player2);
  }

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
            + "full_card_set.txt";

    String gridFilePath = "."
            + File.separator
            + "TESTINGFILES"
            + File.separator
            + "valid_grid.text";

    SetUp setup = new SetUp(cardFilePath, gridFilePath);

    List<IPlayer> playersList = creatingPlayers(args);
    IPlayer player1 = playersList.get(0);
    IPlayer player2 = playersList.get(1);

    GameModel model = new GameModelImpl(setup.setGrid(), player1, player2);

    GameViewGUI viewPlayer1 = new TripleTrioGuiView(model, model.getCurPlayer());
    GameViewGUI viewPlayer2 = new ViewAdapted(new ModelProviderAdapter(model), new PlayerAdapted(model.getOtherPlayer()));
    ((TripleTrioGuiView)viewPlayer2).setLocation(300,300);
    ThreeTriosController controller1 = new ThreeTriosController(model, viewPlayer1, player1);
    ThreeTriosController controller2 = new ThreeTriosController(model, viewPlayer2, player2);
    model.startGame(setup.setCards());
  }
}
