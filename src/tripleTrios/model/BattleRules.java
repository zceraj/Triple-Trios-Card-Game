package tripleTrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the rules logic for the battling phase between cards.
 */
public class BattleRules {

  // Starts the battle after a card is placed
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

  private List<Card> getAdjacentCards(Grid grid, int row, int col) {
    return List.of(
            grid.getCardAt(row - 1, col),
            grid.getCardAt(row + 1, col),
            grid.getCardAt(row, col + 1),
            grid.getCardAt(row, col - 1)
    );
  }

  private Direction decideDirection(int row, int col, Card adjacentCard) {
    int adjRow = adjacentCard.getRow();
    int adjCol = adjacentCard.getCol();

    if (row == adjRow) {
      return col < adjCol ? Direction.EAST : Direction.WEST;
    } else {
      return row < adjRow ? Direction.SOUTH : Direction.NORTH;
    }
  }

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
