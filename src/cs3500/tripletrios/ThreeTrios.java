package cs3500.tripletrios;

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
            {true, false, true},
            {false, true, true},
            {true, false, false}
    };

    ReadOnlyGameModel model = new GameModelImpl(grid, player1, player2);
    TripleTrioGuiView view = new TripleTrioGuiView(model);
    view.setVisible(true);
  }
}