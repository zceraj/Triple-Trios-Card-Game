package tripleTrios.controller;

import java.io.IOException;
import java.util.Map;

import tripleTrios.model.Card;
import tripleTrios.model.CardFileReader;
import tripleTrios.model.GameModel;
import tripleTrios.model.GameModelImpl;
import tripleTrios.model.GridFileReader;
import tripleTrios.view.GameView;

public class tripleTrioController {
  private GameView view;
  private GameModel model;

  /**
   * the constructor for the controller.
   * @param view sets the view that the controller will be using.
   * @param model sets the model that the controller will be using.
   */
  public tripleTrioController(GameView view, GameModel model) {
    this.view = view;
    this.model = model;
  }

  /**
   * method to start the game that calls the helper
   * fuctions which use view and model to execute the commands.
   */
  public void startGame() {
    try {
      // Parse grid configuration
      GridFileReader gridReader = new GridFileReader("path/to/gridConfig.txt");
      boolean[][] grid = gridReader.getGrid();
      System.out.println("Grid:");
      for (boolean[] row : grid) {
        System.out.println(row);
      }

      // Parse card database
      CardFileReader cardParser = new CardFileReader("path/to/cardDatabase.txt");
      Map<String, Card> cards = cardParser.getCardDatabase();
      System.out.println("\nCards:");
      for (Card card : cards.values()) {
        System.out.println(card);
      }
    } catch (IOException e) {
      System.out.println();
    }
    model.startGame();
  }

  public void playTurn(int row, int col, int cardIndex) {
  }

  public void displayGameState() {
  }

}
