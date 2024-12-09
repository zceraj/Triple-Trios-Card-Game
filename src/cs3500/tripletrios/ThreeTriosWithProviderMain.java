//package cs3500.tripletrios;
//
//import java.io.File;
//
//import cs3500.tripletrios.controller.ControllerAdapter;
//import cs3500.tripletrios.controller.SetUp;
//import cs3500.tripletrios.controller.ThreeTriosController;
//import cs3500.tripletrios.model.CellInterface;
//import cs3500.tripletrios.model.GameModel;
//import cs3500.tripletrios.model.GameModelImpl;
//import cs3500.tripletrios.model.HumanPlayer;
//import cs3500.tripletrios.model.IPlayer;
//import cs3500.tripletrios.model.PlayerColor;
//import cs3500.tripletrios.provider.model.ReadOnlyThreeTriosModelInterface;
//import cs3500.tripletrios.provider.view.ThreeTriosGUIView;
//import cs3500.tripletrios.provider.view.ThreeTriosGUIViewInterface;
//import cs3500.tripletrios.view.GameViewGUI;
//import cs3500.tripletrios.view.TripleTrioGuiView;
//
//public final class ThreeTriosWithProviderMain {
//
//  public static void main(String[] args) {
//    String cardFilePath = "." + File.separator + "TESTINGFILES" + File.separator + "full_card_set.txt";
//    String gridFilePath = "." + File.separator + "TESTINGFILES" + File.separator + "valid_grid.text";
//
//    // Set up the game grid and cards
//    SetUp setup = new SetUp(cardFilePath, gridFilePath);
//
//    // Create players
//    IPlayer player1 = new HumanPlayer("Player 1", PlayerColor.RED);
//    IPlayer player2 = new HumanPlayer("Player 2", PlayerColor.BLUE);
//
//    // Create the game model
//    GameModel model = new GameModelImpl(setup.setGrid(), player1, player2);
//
//      // Player 1: Use your implementation
//    GameViewGUI viewPlayer1 = new TripleTrioGuiView(model, player1);
//    ThreeTriosController controller1 = new ThreeTriosController(model, viewPlayer1, player1);
//
//    // Player 2: Use the provider's view with the read-only model
//    ReadOnlyThreeTriosModelInterface readOnlyModel;
//    ThreeTriosGUIViewInterface providerView = new ThreeTriosGUIView(readOnlyModel,
//            cs3500.tripletrios.provider.model.PlayerColor.BLUE);
//
//    // Link controllers via ControllerAdapter
//    ControllerAdapter adapter = new ControllerAdapter(
//            controller1,
//            new ThreeTriosController(model, viewPlayer1, player2)
//    );
//
//    providerView.setController(adapter);
//
//    // Start the game
//    model.startGame(setup.setCards());
//
//    // Make both views visible
//    viewPlayer1.setVisible(true);
//    providerView.setVisible(true);
//  }
//}
