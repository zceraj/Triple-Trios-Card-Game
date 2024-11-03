package cs3500.tripletrios.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Represents the rules logic for the battling phase between cards.
 */
public class BattleRules {

  private final GameModelImpl gameModel;

  public BattleRules(GameModelImpl gameModel) {
    this.gameModel = gameModel;
  }

  /**
   * Starts the battle phase after a card is placed on the grid.
   * @param grid The grid where the card is placed.
   * @param row The row where the card is placed.
   * @param col The column where the card is placed.
   * @param currPlayer The player who placed the card.
   */
  public void startBattle(Grid grid, int row, int col, IPlayer currPlayer) {
    Card placedCard = grid.getCell(row, col).getCard();
    List<Card> adjacentCards = getAdjacentCards(grid, row, col);

    for (Card adjacentCard : adjacentCards) {
      IPlayer adjacentOwner = gameModel.getCellsPlayer(adjacentCard.getRow(),
              adjacentCard.getCol());
      if (adjacentOwner != null && adjacentOwner != currPlayer) {
        Direction direction = decideDirection(row, col, adjacentCard);
        executeBattle(placedCard, adjacentCard, direction);
      }
    }

    comboBattle(grid, row, col, currPlayer);
  }

  /**
   * Executes the comparison between two cards and flips owners based on the result.
   * @param placedCard The card placed by the current player.
   * @param adjacentCard The opposing player's adjacent card.
   * @param direction The direction in which the placed card faces the adjacent card.
   */
  private void executeBattle(Card placedCard, Card adjacentCard, Direction direction) {
    Direction oppositeDirection = direction.getOpposite();

    int placedAttack = parseAttackValue(placedCard.getAttackValue(direction));
    int adjacentAttack = parseAttackValue(adjacentCard.getAttackValue(oppositeDirection));

    if (placedAttack > adjacentAttack) {
      IPlayer placedCardOwner = gameModel.getCellsPlayer(placedCard.getRow(), placedCard.getCol());
      gameModel.updateOwner(adjacentCard.getRow(), adjacentCard.getCol(), placedCardOwner);
    }
  }


  /**
   * Executes combo battles where newly flipped cards can flip cards in chain reactions.
   * @param grid The game grid.
   * @param row The row where the original card was placed.
   * @param col The column where the original card was placed.
   * @param currPlayer The player who placed the card.
   */
  private void comboBattle(Grid grid, int row, int col, IPlayer currPlayer) {
    Set<Card> processedCards = new HashSet<>();
    List<Card> flippedCards = findFlippedCards(grid, currPlayer);

    for (Card flippedCard : flippedCards) {
      if (processedCards.contains(flippedCard)) {
        continue;
      }
      processedCards.add(flippedCard);

      List<Card> adjacentCards = getAdjacentCards(grid, flippedCard.getRow(), flippedCard.getCol());

      for (Card adjacentCard : adjacentCards) {
        IPlayer adjacentOwner = gameModel.getCellsPlayer(
                adjacentCard.getRow(), adjacentCard.getCol());
        if (adjacentOwner != null && adjacentOwner != currPlayer) {
          Direction direction = decideDirection(
                  flippedCard.getRow(), flippedCard.getCol(), adjacentCard);
          executeBattle(flippedCard, adjacentCard, direction);
        }
      }
    }
  }

  /**
   * Retrieves all valid adjacent cards for a given cell on the grid.
   * @param grid The game grid.
   * @param row The row of the current cell.
   * @param col The column of the current cell.
   * @return A list of adjacent cards.
   */
  private List<Card> getAdjacentCards(Grid grid, int row, int col) {
    List<Card> adjacentCards = new ArrayList<>();
    int[][] offsets = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    for (int[] offset : offsets) {
      int newRow = row + offset[0];
      int newCol = col + offset[1];

      if (grid.isValidCell(newRow, newCol)) {
        Cell cell = grid.getCell(newRow, newCol);
        if (cell.isCardCell() && !cell.isEmpty()) {
          adjacentCards.add(cell.getCard());
        }
      }
    }
    return adjacentCards;
  }

  /**
   * Determines the direction from the original cell to an adjacent cell.
   * @param row The row of the original cell.
   * @param col The column of the original cell.
   * @param adjacentCard The adjacent card being examined.
   * @return The direction from the original cell to the adjacent cell.
   */
  private Direction decideDirection(int row, int col, Card adjacentCard) {
    int adjRow = adjacentCard.getRow();
    int adjCol = adjacentCard.getCol();

    if (row == adjRow) {
      return (col < adjCol) ? Direction.EAST : Direction.WEST;
    } else {
      return (row < adjRow) ? Direction.SOUTH : Direction.NORTH;
    }
  }

  /**
   * Finds all cards on the grid that are currently owned by the current player.
   * @param grid The game grid.
   * @param currPlayer The current player.
   * @return A list of cards owned by the current player.
   */
  private List<Card> findFlippedCards(Grid grid, IPlayer currPlayer) {
    List<Card> flippedCards = new ArrayList<>();

    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        Card card = grid.getCell(row, col).getCard();
        if (card != null && gameModel.getCellsPlayer(row, col) == currPlayer) {
          flippedCards.add(card);
        }
      }
    }
    return flippedCards;
  }

  // Parses the attack value of a card, converting "A" to 10.
  private int parseAttackValue(String attackValue) {
    if ("A".equals(attackValue)) {
      return 10;
    }
    try {
      return Integer.parseInt(attackValue);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid attack value: " + attackValue);
    }
  }
}
