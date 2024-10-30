package tripleTrios.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the model for the Triple Trios card game, encapsulating the
 * game logic and managing the state of the game.
 *
 * <p>This class handles the initialization of players and the game grid,
 * facilitates the placement of cards, manages player turns, and checks
 * for game over conditions. It implements the {@link GameModel} interface.</p>
 */
public class GameModelImpl implements GameModel {

  private final Grid grid;
  private IPlayer player1;
  private IPlayer player2;
  private IPlayer currPlayer;
  private boolean gameOver;
  private final Map<Cell, IPlayer> cellsPlayer;

  /**
   * Constructs a GameModelImpl instance with the specified grid and players.
   *
   * @param grid    The game grid
   * @param player1 The first player
   * @param player2 The second player
   * @throws IllegalArgumentException if the grid does not have an odd number
   *                                  of card cells or if players do not have
   *                                  the expected number of cards in hand.
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
   * Starts the game with the given options. The provided deck is used to
   * deal out cards to each player. Modifying the deck given to this method
   * will not modify the game state.
   *
   * @param deck    The deck of cards to use
   * @param player1 The first player
   * @param player2 The second player
   * @throws IllegalStateException    if the game has already started or is over
   * @throws IllegalArgumentException if the grid is not odd or the deck's size
   *                                  is insufficient to set up the game
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
   * Places the specified card at the given row and column in the game grid.
   *
   * @param card The card to place
   * @param row  The row to place the card
   * @param col  The column to place the card
   * @throws IllegalArgumentException if the cell coordinates are invalid or
   *                                  if the cell is not empty
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
   *
   * @return True if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    if (gameOver) {
      return true;
    }
    for (int row = 0; row < grid.getRows(); row++) {
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

  /**
   * Gets the player that is the winner of the game, if the game is over.
   *
   * @return The winning player, or null if the game is not over or there is a tie
   */
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
      return null; // means tie, might need to change to refer to a tie-breaking rule
    }
  }

  /**
   * Gets the player that owns the card in the cell at the specified row and column.
   *
   * @param row The row of the cell
   * @param col The column of the cell
   * @return The player that owns the card in the cell
   */
  public IPlayer getCellsPlayer(int row, int col) {
    Cell cell = grid.getCell(row, col);
    return cellsPlayer.get(cell);
  }

  /**
   * Gets the game grid.
   *
   * @return The grid of the game
   */
  @Override
  public Grid getGameGrid() {
    return grid;
  }

  /**
   * Gets the current player.
   *
   * @return The current player
   */
  @Override
  public IPlayer getCurPlayer() {
    return currPlayer;
  }

  /**
   * Updates the owner of the cell at the specified row and column.
   *
   * @param row      The row of the cell
   * @param col      The column of the cell
   * @param newOwner The new player owning the cell
   */
  public void updateOwner(int row, int col, IPlayer newOwner) {
    Cell cell = grid.getCell(row, col);
    cellsPlayer.put(cell, newOwner);
  }

  /**
   * Advances to the next player's turn.
   */
  @Override
  public void nextTurn() {
    if (currPlayer == player1) {
      currPlayer = player2;
    } else {
      currPlayer = player1;
    }
  }

  /**
   * Initiates a battle at the specified row and column based on the card placed
   * there and the current player.
   *
   * @param row The row of the card
   * @param col The column of the card
   */
  @Override
  public void battles(int row, int col) {
    BattleRules battleRules = new BattleRules(this);
    battleRules.startBattle(grid, row, col, currPlayer);

    nextTurn();
  }

  // Private helper method that counts the cards
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

  private int getCount(Grid grid) {
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
