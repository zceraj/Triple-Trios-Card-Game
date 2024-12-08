//package cs3500.tripletrios.provider;
//
//
//import java.util.List;
//import java.util.Scanner;
//
//import cs3500.tripletrios.controller.GridFileReader;
//import cs3500.tripletrios.model.Grid;
//import cs3500.tripletrios.model.PlayerColor;
//import cs3500.tripletrios.provider.controller.players.Player;
//import cs3500.tripletrios.provider.model.card.CustomCard;
//
///**
// * Main class for the Three Trios game.
// * Accepts command line arguments to determine player types:
// * - "human" for human player
// * - "flipmost" for computer player using FlipMostCards strategy
// * - "corners" for computer player using GoForTheCorners strategy
// */
//public final class ThreeTrios {
//  /**
//   * Main method that starts the Three Trios game.
//   *
//   * @param args Command line arguments specifying player types
//   */
//  public static void main(String[] args) {
//    // Configure players
//    Scanner s = new Scanner(System.in);
//    String[] playerOrComp = s.nextLine().split(" ");
//    String gridPath = "docs/boards/boardWithSeparateGroups.config";
//    Player redPlayer = configurePlayer(playerOrComp[0], PlayerColor.RED);
//    Player bluePlayer = configurePlayer(playerOrComp[1], PlayerColor.BLUE);
//    s.close();
//
//    Grid grid = new ThreeTriosBoard(new GridFileReader().readFile(gridPath));
//    List<CustomCard> deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");
//
//    // Create model
//    ClassicalThreeTriosModel model = new ClassicalThreeTriosModel();
//    model.startGame(grid, deck, true);
//
//    // Create views
//    ThreeTriosGUIViewInterface redView = new ThreeTriosGUIView(model, PlayerColor.RED);
//    ThreeTriosGUIViewInterface blueView = new ThreeTriosGUIView(model, PlayerColor.BLUE);
//
//    // Create controllers for both players and set them to their paired views
//    ControllerManagerInterface controllerManager = new ControllerManager(redPlayer, bluePlayer,
//        model, redView, blueView);
//    redView.setController(controllerManager.getController(PlayerColor.RED));
//    blueView.setController(controllerManager.getController(PlayerColor.BLUE));
//
//    // Register controllers with players
//    redPlayer.callbackFeatures(controllerManager.getController(PlayerColor.RED));
//    bluePlayer.callbackFeatures(controllerManager.getController(PlayerColor.BLUE));
//
//    redView.setVisible(true);
//    blueView.setVisible(false);
//  }
//
//  /**
//   * Configures a player based on the command line argument.
//   *
//   * @param playerType The type of player to create
//   * @param color      The color assigned to the player
//   * @return The configured Player object
//   */
//  private static Player configurePlayer(String playerType, PlayerColor color) {
//    switch (playerType.toLowerCase()) {
//      case "human":
//        return new HumanPlayer(color);
//      case "flipmost":
//        return new ComputerPlayer(color, new MaxFlipsStrategy());
//      case "corners":
//        return new ComputerPlayer(color, new CornerStrategy());
//      default:
//        System.out.println("Invalid player type: " + playerType);
//        System.exit(1);
//        return null;
//    }
//  }
//}
