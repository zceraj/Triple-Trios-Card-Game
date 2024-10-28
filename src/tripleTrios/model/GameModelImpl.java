package tripleTrios.model;

/**
 * Represents the model for the Triple Trios card game.
 * It includes the logic of the game.
 */
public class GameModelImpl implements GameModel{

  private final Grid grid;
  private final Player player1;
  private final Player player2;
  private Player currPlayer;
  private boolean gameOver;

  /**
   * Constructor that creates a game model with the given grid and players.
   * @param grid The game grid
   * @param player1 The first player
   * @param player2 The second player
   */
  public GameModelImpl(Grid grid, Player player1, Player player2) {
    this.grid = grid;
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
  public Player getWinner() {

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

  @Override
  public Grid getGameGrid() {
    return grid;
  }

  @Override
  public Player getCurPlayer() {
    return currPlayer;
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
    BattleRules battleRules = new BattleRules();
    battleRules.startBattle(grid, row, col, currPlayer);

    nextTurn();
  }

  private int countCards(Player player) {
    int count = 0;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        Card card = grid.getCell(row, col).getCard();
        if (card != null && card.getOwner() == player) {
          count++;
        }
      }
    }
    return count;
  }

}
