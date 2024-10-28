package tripleTrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the rules logic for the battling phase between cards.
 */
public class BattleRules {

  /**
   * Starts the battle after a card is placed on the grid.
   * @param grid The grid where the card is placed.
   * @param row The row where the card is placed.
   * @param col The column where the card is placed.
   * @param currPlayer The player who placed the card.
   */
  public void startBattle(Grid grid, int row, int col, Player currPlayer) {
    Card placedCard = grid.getCell(row, col).getCard();
    List<Card> adjacentCards = getAdjacentCards(grid, row, col);

    for (Card adjacentCard : adjacentCards) {
      if (adjacentCard != null && adjacentCard.getOwner() != currPlayer) {
        Direction direction = decideDirection(row, col, adjacentCard);
        attackBattle(placedCard, adjacentCard, direction);
      }
    }
    comboBattle(grid, row, col, currPlayer);
  }

  // Handles one comparison and flips card color if applicable
  private void attackBattle(Card placedCard, Card adjacentCard, Direction direction) {
    Direction oppositeDirection = direction.getOpposite();

    if (placedCard.getAttackValue(direction) > adjacentCard.getAttackValue(oppositeDirection)) {
      adjacentCard.setOwner(placedCard.getOwner());
    }
  }

  // Handles the combo battle
  private void comboBattle(Grid grid, int row, int col, Player currPlayer) {
    List<Card> flippedCards = findFlippedCards(grid, currPlayer);
    for (Card card : flippedCards) {
      List<Card> adjacentCards = getAdjacentCards(grid, card.getRow(), card.getCol());
      for (Card adjacentCard : adjacentCards) {
        if (adjacentCard != null && adjacentCard.getOwner() != currPlayer) {
          Direction direction = decideDirection(card.getRow(), card.getCol(), adjacentCard);
          attackBattle(card, adjacentCard, direction);
        }
      }
    }
  }

  // Gets the adjacent cards to the placed card
  private List<Card> getAdjacentCards(Grid grid, int row, int col) {
    List<Card> adjacentCards = new ArrayList<>();
    for (int[] offset : new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}}) {
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

  // Decides the direction of the attack
  private Direction decideDirection(int row, int col, Card adjacentCard) {
    int adjRow = adjacentCard.getRow();
    int adjCol = adjacentCard.getCol();

    if (row == adjRow) {
      return col < adjCol ? Direction.EAST : Direction.WEST;
    } else {
      return row < adjRow ? Direction.SOUTH : Direction.NORTH;
    }
  }

  // Finds cards that have been flipped to the current player's ownership
  private List<Card> findFlippedCards(Grid grid, Player currPlayer) {
    List<Card> flippedCards = new ArrayList<>();

    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        Card card = grid.getCell(row, col).getCard();
        if (card != null && card.getOwner() == currPlayer) {
          flippedCards.add(card);
        }
      }
    }

    return flippedCards;
  }
}
