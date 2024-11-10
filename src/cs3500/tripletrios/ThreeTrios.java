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
    IPlayer player1 = new HumanPlayer("player1", PlayerColor.BLUE);
    IPlayer player2 = new HumanPlayer("player2", PlayerColor.RED);
    boolean[][] grid = {
            {true, true, false, false, false, false, true}, //row 1
            {true, false, true, false, false, false, true}, //row 2
            {true, false, false, true, false, false, true}, //row 3
            {true, false, false, false, true, false, true}, //row 4
            {true, false, false, false, false, true, true}, //row 5

    };
    List<Card> cards = List.of(
            new Card("Lion", 3, 5, 2, 1),
            new Card("Tiger", 4, 2, 5, 3),
            new Card("Elephant", 5, 1, 4, 2),
            new Card("Giraffe", 2, 4, 3, 5),
            new Card("Zebra", 1, 3, 5, 4),
            new Card("Panda", 4, 4, 4, 4),
            new Card("Wolf", 3, 2, 1, 5),
            new Card("Bear", 5, 3, 4, 2),
            new Card("Fox", 2, 5, 1, 3),
            new Card("Eagle", 3, 4, 5, 1),
            new Card("Shark", 5, 1, 3, 4),
            new Card("Cheetah", 4, 3, 2, 5),
            new Card("Kangaroo", 3, 4, 4, 3),
            new Card("Rhino", 4, 5, 2, 1),
            new Card("Owl", 1, 2, 5, 3),
            new Card("Falcon", 5, 3, 1, 4),
            new Card("Dolphin", 2, 5, 3, 2),
            new Card("Leopard", 3, 1, 5, 4),
            new Card("Hawk", 4, 5, 3, 2),
            new Card("Penguin", 3, 3, 4, 2),
            new Card("Bison", 5, 2, 4, 3),
            new Card("Moose", 2, 3, 5, 4),
            new Card("Koala", 4, 2, 2, 5),
            new Card("Otter", 3, 5, 1, 4),
            new Card("Seal", 1, 4, 3, 5),
            new Card("Lynx", 2, 5, 4, 1),
            new Card("Buffalo", 5, 3, 2, 4),
            new Card("Badger", 4, 4, 5, 1),
            new Card("Raccoon", 3, 1, 5, 3),
            new Card("Squirrel", 2, 3, 5, 4),
            new Card("Gazelle", 2, 3, 5, 2),
            new Card("Hippo", 4, 1, 4, 5),
            new Card("Jaguar", 5, 3, 2, 4),
            new Card("Antelope", 3, 5, 1, 3),
            new Card("Crocodile", 2, 4, 3, 5),
            new Card("Parrot", 1, 5, 4, 2)
    );



    GameModelImpl model = new GameModelImpl(grid, player1, player2);
    model.startGame(cards);
    TripleTrioGuiView view = new TripleTrioGuiView(model);
    view.setVisible(true);
  }
}