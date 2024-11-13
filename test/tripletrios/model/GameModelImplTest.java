package tripletrios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.PlayerColor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests the GameModelImpl class.
 */
public class GameModelImplTest {
  private GameModelImpl game;
  private IPlayer player1;
  private IPlayer player2;
  private List<CardInterface> cards;
  private Grid grid;

  /**
   * sets up the game model to test.
   */
  @BeforeEach
  void setUp() throws Exception {
    player1 = new HumanPlayer("Player1", PlayerColor.BLUE);
    player2 = new HumanPlayer("Player2", PlayerColor.RED);
    cards = List.of(
            new Card("Tiger", 4, 2, 5, 3),
            new Card("Elephant", 5, 1, 4, 2),
            new Card("Giraffe", 2, 4, 3, 5),
            new Card("Zebra", 1, 3, 5, 4),
            new Card("Panda", 4, 4, 4, 4),
            new Card("Wolf", 3, 2, 1, 5)
    );
    //CCX
    //XCC
    //CXC
    boolean[][] gridToSet = {{true, true, false},
            {false, true, true},
            {false, true, false}};
    this.grid = new Grid(gridToSet);

    game = new GameModelImpl(
            gridToSet,
            player1,
            player2);

    this.game.startGame(cards);
  }


  @Test
  public void testGetCount() {
    assertEquals(4, this.game.getGameGrid().getCount());

  }

  @Test
  public void testGetGrid() {
    assertNotNull(game.getGameGrid());
    assertEquals(3, game.getGameGrid().getRows());
    assertEquals(3, game.getGameGrid().getCols());

    //checks cell types true for card cells and false for holes
    Assertions.assertTrue(game.getGameGrid().getCell(0, 0).isCardCell());
    Assertions.assertFalse(game.getGameGrid().getCell(0, 1).isCardCell());
  }

  @Test
  public void testBattles() {
    CardInterface playersCard = player1.getHand().get(0);
    CardInterface opponentsCard = player2.getHand().get(0);

    game.placeCard(playersCard, 0, 0);
    game.placeCard(opponentsCard, 1, 2);

    game.battles(0, 0);

    assertEquals(player1, game.getCellsPlayer(1, 2));
  }


  @Test
  public void testNextTurn() {
    assertEquals(player1, game.getCurPlayer());
    game.nextTurn();
    assertEquals(player2, game.getCurPlayer());
    game.nextTurn();
    assertEquals(player1, game.getCurPlayer());
  }

  @Test
  public void testUpdateOwner() {
    CardInterface playersCard = player1.getHand().get(0);
    game.placeCard(playersCard, 0, 0);

    game.updateOwner(0, 0, player2);

    assertEquals(player2, game.getCellsPlayer(0, 0));
  }


  @Test
  public void testGetCurPlayer() {
    assertEquals(player1, game.getCurPlayer());
    assertNotEquals(player2, game.getCurPlayer());
  }

  @Test
  public void testGetGameGrid() {
    assertEquals("[X] [ ] [ ]\n" +
            "[X] [ ] [X]\n" +
            "[ ] [ ] [X]", game.getGameGrid().toString());
  }

  @Test
  public void testGetCellsPlayer() {
    CardInterface playersCard = player1.getHand().get(0);
    game.placeCard(playersCard, 0, 0);

    assertEquals(player1, game.getCellsPlayer(0, 0));
  }

  @Test
  public void testGetWinner() {
    CardInterface playersCard1 = player1.getHand().get(0);
    CardInterface playersCard2 = player1.getHand().get(1);
    CardInterface opponentsCard = player2.getHand().get(0);

    game.placeCard(playersCard1, 0, 0);
    game.placeCard(playersCard2, 1, 0);
    game.placeCard(opponentsCard, 1, 2);
    game.placeCard(opponentsCard, 2, 2);

    IPlayer winner = game.getWinner();

    assertEquals(player1, winner);
  }



  @Test
  public void testIsGameOver() {
    assertFalse(game.isGameOver());

    for (int row = 0; row < game.getGameGrid().getRows(); row++) {
      for (int col = 0; col < game.getGameGrid().getCols(); col++) {
        if (game.getGameGrid().getCell(row, col).isCardCell()
                && game.getGameGrid().getCell(row, col).isEmpty()) {
          CardInterface card = player1.getHand().get(0);
          game.placeCard(card, row, col);
        }
      }
    }
    assertTrue(game.isGameOver());
  }


  @Test
  public void testPlaceCard() {
    CardInterface card = player1.getHand().get(0);
    game.placeCard(card, 0, 0);
    assertEquals(card, game.getGameGrid().getCardAt(0, 0));
  }

  @Test
  public void testPlayerCantPlayWrongTurn() {
    CardInterface card = player2.getHand().get(0);
    assertThrows(IllegalArgumentException.class, () -> game.placeCard(card, 1, 1));
  }

  @Test
  void testStartGameInitializesGridCorrectly() {
    assertNotNull(game.getGameGrid(), "Grid should be initialized.");
    assertEquals(3, game.getGameGrid().getRows(), "Grid should have correct number of rows.");
    assertEquals(3, game.getGameGrid().getCols(), "Grid should have correct number of columns.");
  }

  @Test
  void testStartGameDistributesCards() {
    int expectedHandSize = (game.getGameGrid().getCount() + 1) / 2;
    assertEquals(expectedHandSize, player1.getHand().size(),
            "Player 1 should have correct hand size.");
    assertEquals(expectedHandSize, player2.getHand().size(),
            "Player 2 should have correct hand size.");
  }

  @Test
  public void testPlayerInvalidLocation() {
    CardInterface card = player1.getHand().get(0);
    assertThrows(IllegalArgumentException.class, () -> game.placeCard(card, 4, 5));
  }

  @Test
  void testGameOverFlagIsInitiallyFalse() {
    assertFalse("Game should not be over at start.", game.isGameOver());
  }

  @Test
  void testCurrentPlayerIsPlayer1AtStart() {
    assertEquals(player1, game.getCurPlayer(), "Player 1 should start the game.");
  }

  @Test
  public void testStartGame_ValidDeck() {
    List<Card> deck = new ArrayList<>();

    for (int i = 1; i <= 10; i++) {
      int validAttackValue = Math.min(i, 10);
      deck.add(new Card(
              "Card " + i,
              validAttackValue,
              validAttackValue,
              validAttackValue,
              validAttackValue));
    }

    int expectedHandSize = (game.getGameGrid().getCount() + 1) / 2;
    assertEquals(expectedHandSize, player1.getHand().size());
    assertEquals(expectedHandSize, player2.getHand().size());
    assertEquals(player1, game.getCurPlayer());
    assertFalse(game.isGameOver());
  }


  @Test
  public void testStartGame_PlayerHandsCorrectAfterStart() {
    List<String> showHand1 = new ArrayList<>();
    for (CardInterface card : player1.getHand()) {
      showHand1.add(card.toString());
    }
    List<String> showHand2 = new ArrayList<>();
    for (CardInterface card : player2.getHand()) {
      showHand2.add(card.toString());
    }

    assertEquals(List.of("Lion 3 5 2 1 ", "Tiger 4 2 5 3 ").toString(), showHand1.toString());
    assertEquals(List.of("Elephant 5 1 4 2 , Giraffe 2 4 3 5 ").toString(), showHand2.toString());
  }

  @Test
  public void testFlipPersonalCard() {
    CardInterface card = player1.getHand().get(0);
    game.placeCard(card, 0, 0);

    game.battles(0, 0);

    assertEquals(player1, game.getCellsPlayer(0, 0));
  }


  @Test
  public void testFlipOpponentCard() {
    CardInterface playersCard = player1.getHand().get(0);
    CardInterface opponentsCard = player2.getHand().get(0);

    game.placeCard(playersCard, 0, 0);
    game.placeCard(opponentsCard, 1, 0);

    game.battles(0, 0);

    assertEquals(player1, game.getCellsPlayer(1, 0));
  }


  @Test
  public void testFlippingTiles() {
    CardInterface player1Card1 = player1.getHand().get(0);
    CardInterface player2Card1 = player2.getHand().get(0);
    CardInterface player2Card2 = player2.getHand().get(1);

    game.placeCard(player1Card1, 0, 0);
    game.placeCard(player2Card1, 2, 2);
    game.placeCard(player2Card2, 1, 0);
    game.battles(0, 0);

    assertNull(game.getCellsPlayer(0, 1));
    assertEquals(player1, game.getCellsPlayer(1, 0));
  }

  @Test
  //test the get score method in between plays
  public void testGetScore() {
    CardInterface playersCard = player1.getHand().get(0);
    game.placeCard(playersCard, 0, 0);
    assertEquals(1, game.getScore(player1));
  }

}
