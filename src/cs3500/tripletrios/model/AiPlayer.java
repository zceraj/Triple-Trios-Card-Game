package cs3500.tripletrios.model;

import cs3500.tripletrios.strategy.MovesInterface;
import cs3500.tripletrios.strategy.StrategyInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an AI player in the game.
 * Implements the IPlayer interface.
 * This class uses a given strategy to determine its moves.
 */
public class AiPlayer implements IPlayer {
  private final String name;
  private final PlayerColor color;
  private final List<CardInterface> hand;
  private final List<CardInterface> allCards;
  private StrategyInterface strategy;

  /**
   * Constructs an AI player with the specified name, color, and strategy.
   *
   * @param name     the name of the AI player
   * @param color    the color representing the AI player's identity
   */
  public AiPlayer(String name, PlayerColor color) {
    this.name = name;
    this.color = color;
    this.hand = new ArrayList<>();
    this.allCards = new ArrayList<>();
    this.strategy = null;
  }

  /**
   * Retrieves the name of the player.
   *
   * @return the name of the player as a {@code String}.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Retrieves the color of the player.
   * @return the color of the player
   */
  @Override
  public String getColor() {
    return color.toString();
  }

  /**
   * Retrieves the hand of the player.
   * @return the hand of the player
   */
  @Override
  public List<CardInterface> getCurrentHand() {
    return new ArrayList<>(hand); // Return a copy to prevent outside modification.
  }

  /**
   * Adds a card to the player's hand.
   * @param card to be added to the player's hand.
   */
  @Override
  public void addCardToHand(CardInterface card) {

    hand.add(card);
    allCards.add(card);

  }

  /**
   * Removes a card from the player's hand.
   * @param card to be removed from the player's hand.
   */
  @Override
  public void removeCardFromHand(CardInterface card) {
    hand.remove(card);
  }

  /**
   * Sets the hand of the player.
   * @param playerHand the hand to set
   * @throws IllegalArgumentException if the hand is null
   */
  @Override
  public void setCurrentHand(List<CardInterface> playerHand) {
    if (playerHand == null) {
      throw new IllegalArgumentException("Hand cannot be null.");
    }
    this.hand.clear();
    this.hand.addAll(playerHand);
  }

  /**
   * Places the specified card at the given row and column in the game grid.
   * @param card the card to be placed
   * @param row  the row to place the card
   * @param col the column to place the card
   */
  @Override
  public void placeTheCard(CardInterface card, int row, int col) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    }
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Row and column must be non-negative.");
    }

    MovesInterface bestMove = determineNextMove();
    row = bestMove.getRow();
    col = bestMove.getCol();
    card.addRow(row);
    card.addCol(col);
    hand.remove(card);
  }


  /**
   * Determines the best move using the assigned strategy.
   *
   * @return the best move determined by the strategy
   */
  public MovesInterface determineNextMove() {
    if (strategy == null) {
      throw new IllegalStateException("No strategy assigned to AI player.");
    }

    MovesInterface bestMove = strategy.getBestMove(this);
    if (bestMove == null || bestMove.getRow() < 0 || bestMove.getCol() < 0) {
      throw new IllegalStateException("No valid move available for AI player.");
    }
    return bestMove;
  }

  /**
   * Sets a strategy for the AI player to use that is refered to in the determineNextMove.
   * @param strategy the strategy to set
   * @throws IllegalArgumentException if the strategy is null
   */
  public void setStrategy(StrategyInterface strategy) {
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy cannot be null.");
    }
    this.strategy = strategy;
  }

  @Override
  public List<CardInterface> getAllCards() {
    return new ArrayList<>(allCards);
  }
}
