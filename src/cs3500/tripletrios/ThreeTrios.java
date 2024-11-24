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
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.strategy.StrategyInterface;
import cs3500.tripletrios.strategy.StrategyOne;
import cs3500.tripletrios.strategy.StrategyThree;
import cs3500.tripletrios.strategy.StrategyTwo;
import cs3500.tripletrios.strategy.StrategyFour;
import cs3500.tripletrios.strategy.ChainStrategy;
import cs3500.tripletrios.view.GameViewGUI;
import cs3500.tripletrios.view.TripleTrioGuiView;

/**
 * the entry point for running the Triple Trios game.
 * It initializes the game model, players, grid configuration, and cards,
 * then launches the GUI view.
 */

public final class ThreeTrios {

  private static IPlayer createPlayer(PlayerColor color, String arg)
          throws IllegalArgumentException {
    IPlayer player = null;
    String playerName;

    if (color == PlayerColor.BLUE) {
      playerName = "player2";
    }
    else {
      playerName = "player1";
    }

    switch (arg) {
      case "AI":
      case "Strategy1":
      case "Strategy2":
      case "Strategy3":
      case "Strategy4":
      case "AIPlayer":
        player = new AiPlayer(playerName, color);
        break;
      case "Human":
      case "HumanPlayer":
        player = new HumanPlayer(playerName, color);
        break;
      default:
        throw new IllegalArgumentException("Unknown player or strategy: " + arg);
    }

    return player;
  }

  private static List<IPlayer> creatingPlayers(String[] args) {
    IPlayer player1 = null;
    IPlayer player2 = null;

    if (args == null || args.length == 0) {
      System.out.println("No arguments provided. Game will be started with 2 human players. Press q"
              +
              "to quit.");
      player1 = new HumanPlayer("player1", PlayerColor.RED);
      player2 = new HumanPlayer("player2", PlayerColor.BLUE);
    }

    if (args.length == 1) {
      player1 = createPlayer(PlayerColor.RED, args[0]);
      System.out.println("Player 1: " + player1);
      System.out.println("Please add another player");
      Scanner readPlayer2 = new Scanner(System.in);
      player2 = createPlayer(PlayerColor.BLUE, readPlayer2.toString());
    }

    if (args.length >= 3) {
      System.out.println("Too many arguments inputted, this game only supports 2 players. The game "
          + "will be started with the first two inputs. Press q to quit.");
    }

    if (args.length >= 2) {
      player1 = createPlayer(PlayerColor.RED, args[0]);
      player2 = createPlayer(PlayerColor.BLUE, args[1]);
    }

    List<IPlayer> playersToReturn = new ArrayList<>();
    playersToReturn.add(player1);
    playersToReturn.add(player2);
    return playersToReturn;
  }


  private static void setStrats(GameModel model, IPlayer playerUno,
                                IPlayer playerDos, String[] args) {
    IPlayer player1 = playerUno;
    IPlayer player2 = playerDos;
    boolean canBeStrat4 = false;

    StrategyInterface player2Strat;
    StrategyInterface player1Strat;

    if (player1 instanceof AiPlayer && player2 instanceof AiPlayer) {
      canBeStrat4 = true;
    }
    if (player1 instanceof AiPlayer) {
      player1Strat = setStrat(model, player1, args[0]);
      player1.setStrategy(player1Strat);
    }
    if (player2 instanceof AiPlayer) {
      player2Strat = setStrat(model, player2, args[1]);
      player2.setStrategy(player2Strat);
    }
  }

  private static StrategyInterface setStrat(GameModel model, IPlayer player, String arg) {
    StrategyInterface strategy = null;

    if (player instanceof HumanPlayer) {
      throw new IllegalArgumentException("can't set strat for a human player ");
    }

    String[] input = arg.split(" ");
    if (input.length == 1) {
      switch (input[0]) {
        case "Strategy1":
          strategy = new StrategyOne(model);
          break;
        case "Strategy2":
          strategy = new StrategyTwo(model);
          break;
        case "Strategy3":
          strategy = new StrategyThree(model);
          break;
        case "Strategy4":
          strategy = new StrategyFour(model, new StrategyOne(model));
          break;
        default:
          strategy = new StrategyOne(model);
      }
    }

    else {
      ChainStrategy stratsList = new ChainStrategy(model.getGameGrid());
      for (int i = 0; i < input.length; i++) {
        switch (input[i]) {
          case "Strategy1":
            stratsList.addStrategy(new StrategyOne(model));
            break;
          case "Strategy2":
            stratsList.addStrategy(new StrategyTwo(model));
            break;
          case "Strategy3":
            stratsList.addStrategy(new StrategyThree(model));
            break;
          case "Strategy4":
            stratsList.addStrategy(new StrategyFour(model, new StrategyOne(model)));
            break;
          default:
            stratsList.addStrategy(null);
        }
      }
    }

    return strategy;
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

    setStrats(model, player1, player2, args);

    GameViewGUI viewPlayer1 = new TripleTrioGuiView(model, model.getCurPlayer());
    GameViewGUI viewPlayer2 = new TripleTrioGuiView(model, model.getOtherPlayer());
    ThreeTriosController controller1 = new ThreeTriosController(model, viewPlayer1);
    ThreeTriosController controller2 = new ThreeTriosController(model, viewPlayer2);
    model.startGame(setup.setCards());
  }
}
