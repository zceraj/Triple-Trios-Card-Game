package tripletrios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests the GameModelImpl class.
 */
public class GameModelImplTest {
  private GameModelImpl game;
  private IPlayer player1;
  private IPlayer player2;

  /**
   * sets up the game model to test.
   */
  @BeforeEach
  void setUp() throws Exception {
    player1 = new HumanPlayer("Player1", PlayerColor.BLUE);
    player2 = new HumanPlayer("Player2", PlayerColor.RED);

    List<Card> deck = new ArrayList<>();
    deck.add(new Card("Card 1", 1, 2, 3, 4));
    deck.add(new Card("Card 2", 2, 4, 6, 8));
    deck.add(new Card("Card 3", 3, 6, 9, 9));
    deck.add(new Card("Card 4", 4, 8, 4, 1));
    deck.add(new Card("Card 5", 5, 3, 2, 1));

    int expectedHandSize = (deck.size() + 1) / 2;
    player1.setHand(deck.subList(0, expectedHandSize));
    player2.setHand(deck.subList(expectedHandSize, deck.size()));

    // Read grid from file and create Grid object
    String gridFilePath = "." + File.separator + "TESTINGFILES" + File.separator + "valid_grid.txt";
    GridFileReader gridReader = new GridFileReader(gridFilePath);
    Grid grid = new Grid(gridReader.getGrid());

    game = new GameModelImpl(grid, player1, player2);
  }


  @Test
  public void testGetCount() {
    assertEquals(5, game.getTotalCardCells(game.getGameGrid()));

  }

  @Test
  public void testGetGrid() {
    assertNotNull(game.getGameGrid());
    assertEquals(3, game.getGameGrid().getRows());
    assertEquals(3, game.getGameGrid().getCols());

    //checks cell types true for card cells and false for holes
    assertEquals(true, game.getGameGrid().getCell(0, 0).isCardCell());
    assertEquals(false, game.getGameGrid().getCell(0, 1).isCardCell());
  }

  @Test
  public void testBattles() {
    Card playersCard = player1.getHand().get(0);
    Card opponentsCard = player2.getHand().get(0);

    game.placeCard(playersCard, 0, 0);
    game.placeCard(opponentsCard, 0, 2);

    game.battles(0, 0);

    assertEquals(player1, game.getCellsPlayer(0, 2));
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
    Card playersCard = player1.getHand().get(0);
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
    boolean[][] cells = {
            {true, true, true},
            {false, true, false},
            {true, false, false}
    };
    Grid grid = new Grid(cells);

    assertEquals(grid, game.getGameGrid());
  }

  @Test
  public void testGetCellsPlayer() {
    Card playersCard = player1.getHand().get(0);
    game.placeCard(playersCard, 0, 0);

    assertEquals(player1, game.getCellsPlayer(0, 0));
  }

  @Test
  public void testGetWinner() {
    Card playersCard1 = player1.getHand().get(0);
    Card playersCard2 = player1.getHand().get(1);
    Card opponentsCard = player2.getHand().get(0);

    game.placeCard(playersCard1, 0, 0);
    game.placeCard(playersCard2, 2, 0);
    game.placeCard(opponentsCard, 0, 2);

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
          Card card = player1.getHand().get(0);
          game.placeCard(card, row, col);
        }
      }
    }
    assertTrue(game.isGameOver());
  }


  @Test
  public void testPlaceCard() {
    Card card = player1.getHand().get(0);
    game.placeCard(card, 0, 0);
    assertEquals(card, game.getGameGrid().getCardAt(0, 0));
  }


  @Test
  void testStartGameInitializesGridCorrectly() {
    assertNotNull(game.getGameGrid(), "Grid should be initialized.");
    assertEquals(3, game.getGameGrid().getRows(), "Grid should have correct number of rows.");
    assertEquals(3, game.getGameGrid().getCols(), "Grid should have correct number of columns.");
  }

  @Test
  void testStartGameDistributesCards() {
    int expectedHandSize = (game.getTotalCardCells(game.getGameGrid()) + 1) / 2;
    assertEquals(expectedHandSize, player1.getHand().size(),
            "Player 1 should have correct hand size.");
    assertEquals(expectedHandSize, player2.getHand().size(),
            "Player 2 should have correct hand size.");
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
  public void testInvalidGrid_ThrowsException() {
    boolean[][] invalidCells = {
            {true, true, true},
            {false, true, false},
            {false, true, true}
    };
    Grid invalidGrid = new Grid(invalidCells);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new GameModelImpl(invalidGrid, player1, player2);
    });

    assertEquals("Grid must have an odd number of card cells.", exception.getMessage());
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

    int expectedHandSize = (game.getTotalCardCells(game.getGameGrid()) + 1) / 2;
    assertEquals(expectedHandSize, player1.getHand().size());
    assertEquals(expectedHandSize, player2.getHand().size());
    assertEquals(player1, game.getCurPlayer());
    assertFalse(game.isGameOver());
  }


  @Test
  public void testStartGame_PlayerHandsCorrectAfterStart() {
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
    int expectedHandSize = (game.getTotalCardCells(game.getGameGrid()) + 1) / 2;
    List<Card> expectedPlayer1Hand = deck.subList(0, expectedHandSize);
    List<Card> expectedPlayer2Hand = deck.subList(expectedHandSize, 2 * expectedHandSize);

    assertEquals(expectedPlayer1Hand, player1.getHand());
    assertEquals(expectedPlayer2Hand, player2.getHand());
  }

  @Test
  public void testFlipOwnCard() {
    Card card = player1.getHand().get(0);
    game.placeCard(card, 0, 0);

    game.battles(0, 0);

    assertEquals(player1, game.getCellsPlayer(0, 0));
  }


  @Test
  public void testFlipOpponentCard() {
    Card playersCard = player1.getHand().get(0);
    Card opponentsCard = player2.getHand().get(0);

    game.placeCard(playersCard, 0, 0);
    game.placeCard(opponentsCard, 0, 2);

    game.battles(0, 0);

    assertEquals(player1, game.getCellsPlayer(0, 2));
  }

  @Test
  public void testPlayerCantPlayWhenNotTheirTurn() {
    Card card = player2.getHand().get(0);
    assertThrows(IllegalStateException.class, () -> game.placeCard(card, 0, 0));
  }

  @Test
  public void testPlayerCantPlayToOutOfBoundsLocation() {
    Card card = player1.getHand().get(0);
    assertThrows(IllegalArgumentException.class, () -> game.placeCard(card, 4, 5));
  }

  @Test
  public void testTilesAreFlippedCorrectly() {
    Card player1Card1 = player1.getHand().get(0);
    Card player2Card1 = player2.getHand().get(0);
    Card player2Card2 = player2.getHand().get(1);

    game.placeCard(player1Card1, 0, 0);
    game.placeCard(player2Card1, 0, 1);
    game.placeCard(player2Card2, 1, 0);
    game.battles(0, 0);

    assertEquals(player1, game.getCellsPlayer(0, 1));
    assertEquals(player1, game.getCellsPlayer(1, 0));
  }

}
