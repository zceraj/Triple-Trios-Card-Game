package tripletrios.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.tripletrios.model.BattleRules;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.observing.Observable;

/**
 * mock for my model.
 */
public class ControllerMockModel extends Observable implements GameModel {
  private final Grid grid;
  private final IPlayer player1;
  private final IPlayer player2;
  private IPlayer currPlayer;
  private boolean gameOver;
  private boolean gameStarted = false;
  private final Map<Cell, IPlayer> cellsPlayer;

  /**
   * Constructs a GameModelImpl instance with the specified grid and players.
   *
   * @param grid    a grid that isn't yet of class grid.
   * @param player1 the first player.
   * @param player2 the second player.
   * @throws RuntimeException if the grid file cannot be read
   */
  public ControllerMockModel(
          boolean[][] grid,
          IPlayer player1,
          IPlayer player2) throws RuntimeException {
    Grid trialGrid = new Grid(grid);
    if (!isGridOdd(trialGrid)) {
      throw new IllegalArgumentException("Grid must have an odd number of card cells.");
    } else {
      this.grid = trialGrid;
    }
    this.player1 = player1;
    this.player2 = player2;
    this.currPlayer = player1;
    this.gameOver = false;
    this.cellsPlayer = new HashMap<>();
  }

  /**
   * Starts the game with the given options. The provided deck is used to
   * deal out cards to each player. Modifying the deck given to this method
   * will not modify the game state.
   *
   * @param cards is a list of cards passed in.
   * @throws IllegalStateException    if the game has already started or is over
   * @throws IllegalArgumentException if the grid is not odd or the deck's size
   *                                  is insufficient to set up the game
   */
  @Override
  public void startGame(List<CardInterface> cards) {
    if (this.gameStarted) {
      throw new IllegalStateException("Game has already started.");
    }
    if (this.gameOver) {
      throw new IllegalStateException("Game is over.");
    }

    int totalCardCells = grid.getCount();

    if (cards.size() < totalCardCells) {
      throw new IllegalArgumentException("Deck must have enough cards to fill the grid.");
    }

    List<CardInterface> player1Hand = new ArrayList<>();
    List<CardInterface> player2Hand = new ArrayList<>();

    for (int i = 0; i < cards.size(); i++) {
      if (i % 2 == 0) {
        player1Hand.add(cards.get(i));
      } else {
        player2Hand.add(cards.get(i));
      }
    }

    player1.setCurrentHand(player1Hand);
    player2.setCurrentHand(player2Hand);

    this.currPlayer = player1;
    this.gameStarted = true;
    notifyObservers();
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
  public void placeCard(CardInterface card, int row, int col) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    // Check if the cell coordinates are valid
    if (!grid.isValidCell(row, col) || row < 0 || col < 0
            || row >= grid.getRows() || col >= grid.getCols()) {
      throw new IllegalArgumentException("Invalid cell coordinates: (" + row + ", " + col + ")");
    }

    Cell targetCell = grid.getCell(row, col);
    if (!targetCell.isCardCell() || !targetCell.isEmpty()) {
      throw new IllegalArgumentException("Cannot place card in this cell.");
    }

    card.addRow(row);
    card.addCol(col);
    System.out.println("Adding card position: (" + row + ", " + col + ")");

    currPlayer.placeTheCard(card, row, col);
    targetCell.setCard(card);

    grid.updateCell(row, col, targetCell);
    cellsPlayer.put(targetCell, currPlayer);

    battles(row, col);

    if (isGameOver()) {
      gameOver = true;
    } else {
      nextTurn();
      notifyObservers();
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
      notifyObservers();
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
    notifyObservers();
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
    boolean[][] cellTypes = new boolean[grid.getRows()][grid.getCols()];
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        cellTypes[row][col] = grid.getCell(row, col).isCardCell();
      }
    }
    return new Grid(this.grid);
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
  @Override
  public void updateOwner(int row, int col, IPlayer newOwner) {
    Cell orignalCell = grid.getCell(row, col);
    Cell updatedCell = new Cell(orignalCell);
    cellsPlayer.put(updatedCell, newOwner);
  }

  @Override
  public boolean justStarted() {
    return false;
  }

  @Override
  public void setJustStarted(boolean justStarted) {

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

  /**
   * Gets the score for a player in the game.
   *
   * @param player The player to get the score of
   * @return The score of the player
   */
  @Override
  public int getScore(IPlayer player) {
    return countCards(player);
  }


  // Private helper method that counts the cards
  private int countCards(IPlayer player) {
    int count = 0;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        CardInterface card = grid.getCell(row, col).getCard();
        if (card != null && getCellsPlayer(row, col) == player) {
          count++;
        }
      }
    }
    return count;
  }

  // checks if the gird has an odd number of cells
  private boolean isGridOdd(Grid grid) {
    int cardCellCount = grid.getCount();
    return cardCellCount % 2 == 1;
  }

  /**
   * Returns the player who is not the current player.
   *
   * @return the other player in the game, opposite to the current player
   */
  @Override
  public IPlayer getOtherPlayer() {
    if (currPlayer == player2) {
      return player1;
    }
    return player2;
  }

  /**
   * Determines the owner of the specified room.
   *
   * @param card the card to locate
   * @return the player who has the card or null if no player was found.
   */
  @Override
  public IPlayer getPlayerFromCard(CardInterface card) {
    if (player1.getCurrentHand().contains(card)) {
      return player1;
    } else if (player2.getCurrentHand().contains(card)) {
      return player2;
    }
    return null;
  }

  @Override
  public boolean isGameStarted() {
    return gameStarted;
  }


}
