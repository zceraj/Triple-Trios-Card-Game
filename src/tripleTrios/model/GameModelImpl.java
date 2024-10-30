package tripleTrios.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the model for the Triple Trios card game.
 * It includes the logic of the game.
 */
public class GameModelImpl implements GameModel{

  private final Grid grid;
  private IPlayer player1;
  private IPlayer player2;
  private IPlayer currPlayer;
  private boolean gameOver;

  private final Map<Cell, IPlayer> cellsPlayer;

  /**
   * Constructor that creates a game model with the given grid and players.
   * @param grid The game grid
   * @param player1 The first player
   * @param player2 The second player
   */
  public GameModelImpl(Grid grid, IPlayer player1, IPlayer player2) {
    this.grid = grid;
    this.player1 = player1;
    this.player2 = player2;
    this.currPlayer = player1;
    this.gameOver = false;
    this.cellsPlayer = new HashMap<>();

    if (isGridOdd(grid)) {
      throw new IllegalArgumentException("Grid must have an odd number of card cells.");
    }
    int expectedHandSize = (getTotalCardCells(grid) + 1) / 2;
    if (player1.getHand().size() != expectedHandSize || player2.getHand().size() != expectedHandSize) {
      throw new IllegalArgumentException("Players must have the correct number of cards in their hand.");
    }
  }


  /**
   * Starts the game with the given options. The deck given is used
   * to deal out the cards to each player . Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param deck The deck of cards to use
   * @param player1 The first player
   * @param player2 The second player
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if the gird is not odd
   * @throws IllegalArgumentException if deck's size is not large enough to set up the game
   */
  @Override
  public void startGame(List<Card> deck, IPlayer player1, IPlayer player2) {
    if (!isGridOdd(grid)) {
      throw new IllegalArgumentException("Grid must have an odd number of card cells.");
    }
    if (!isGameOver()) {
      throw new IllegalStateException("Game has already started.");
    }

    int totalCardCells = getTotalCardCells(grid);
    int expectedHandSize = (totalCardCells + 1) / 2;

    if (deck.size() < totalCardCells) {
      throw new IllegalArgumentException("Deck must have enough cards to fill the grid.");
    }

    List<Card> player1Hand = new ArrayList<>(deck.subList(0, expectedHandSize));
    List<Card> player2Hand = new ArrayList<>(deck.subList(expectedHandSize, 2 * expectedHandSize));

    player1.setHand(player1Hand);
    player2.setHand(player2Hand);

    this.player1 = player1;
    this.player2 = player2;
    this.currPlayer = player1;

    this.gameOver = false;

  }


  /**
   * Places the given card at the specified row and column in the game grid.
   * @param card The card to place
   * @param row The row to place the card
   * @param col The column to place the card
   */
  @Override
  public void placeCard(Card card, int row, int col) {
    if (!grid.isValidCell(row, col)) {
      throw new IllegalArgumentException("Invalid cell coordinates.");
    }

    Cell cell = grid.getCell(row, col);
    if (!cell.isCardCell() || !cell.isEmpty()) {
      throw new IllegalArgumentException("Cannot place card in this cell.");
    }
    card.addRow(row);
    card.addCol(col);

    cell.setCard(card);
    cellsPlayer.put(cell, currPlayer);

    battles(row, col);

    if (isGameOver()) {
      gameOver = true;
    } else {
      nextTurn();
    }
  }


  /**
   * Checks if the game is over.
   * @return True if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    if (gameOver) {
      return true;
    }
    for (int row = 0; row < grid.getCols(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (grid.getCell(row, col).isCardCell() && grid.getCell(row, col).isEmpty()) {
          gameOver = false;
          return false;
        }
      }
    }
    gameOver = true;
    return true;
  }

  @Override
  public IPlayer getWinner() {

    if (!gameOver) {
      return null;
    }
    int player1Count = countCards(player1);
    int player2Count = countCards(player2);

    if (player1Count > player2Count) {
      return player1;
    } else if (player2Count > player1Count) {
      return player2;
    } else {
      return null; // means tie, might need to change to refer to a tie breaking rule?
    }

  }

  /**
   * Gets the player that owns the card in the cell at the given row and column.
   * @param row The row of the cell
   * @param col The column of the cell
   * @return The player that owns the card in the cell
   */
  public IPlayer getCellsPlayer(int row, int col) {
    Cell cell = grid.getCell(row, col);
    return cellsPlayer.get(cell);
  }

  @Override
  public Grid getGameGrid() {
    return grid;
  }

  @Override
  public IPlayer getCurPlayer() {
    return currPlayer;
  }

  public void updateOwner(int row, int col, IPlayer newOwner) {
    Cell cell = grid.getCell(row, col);
    cellsPlayer.put(cell, newOwner);
  }

  @Override
  public void nextTurn() {
    if (currPlayer == player1) {
      currPlayer = player2;
    } else {
      currPlayer = player1;
    }
  }

  @Override
  public void battles(int row, int col) {
    BattleRules battleRules = new BattleRules(this);
    battleRules.startBattle(grid, row, col, currPlayer);

    nextTurn();
  }

  private int countCards(IPlayer player) {
    int count = 0;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        Card card = grid.getCell(row, col).getCard();
        if (card != null && getCellsPlayer(row, col) == player) {
          count++;
        }
      }
    }
    return count;
  }

  private int getTotalCardCells(Grid grid) {
    return getCount(grid);
  }


  private boolean isGridOdd(Grid grid) {
    int cardCellCount = getCount(grid);
    return cardCellCount % 2 != 0;
  }

  private static int getCount(Grid grid) {
    int count = 0;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (grid.getCell(row, col).isCardCell()) {
          count++;
        }
      }
    }
    return count;
  }


}
