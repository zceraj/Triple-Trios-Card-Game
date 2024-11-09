package cs3500.tripletrios;

import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.model.ReadOnlyGameModel;
import cs3500.tripletrios.view.TripleTrioGuiView;

public final class ThreeTrios {
  public static void main(String[] args) {
    IPlayer player1 = new HumanPlayer("player1", PlayerColor.RED);
    IPlayer player2 = new HumanPlayer("player2", PlayerColor.BLUE);
    boolean[][] grid = {
            {true, false, false},
            {true, false, true},
            {false, false, true}
    };
    List<Card> cards = List.of(
            new Card("Tiger", 4, 2, 5, 3),
            new Card("Elephant", 5, 1, 4, 2),
            new Card("Giraffe", 2, 4, 3, 5),
            new Card("Zebra", 1, 3, 5, 4),
            new Card("Panda", 4, 4, 4, 4),
            new Card("Wolf", 3, 2, 1, 5)
    );

    GameModelImpl model = new GameModelImpl(grid, player1, player2);
    model.startGame(cards);
    TripleTrioGuiView view = new TripleTrioGuiView(model);
    view.setVisible(true);
  }
}