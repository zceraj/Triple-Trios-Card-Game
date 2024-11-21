package cs3500.tripletrios.model;

import cs3500.tripletrios.strategy.Moves;
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
  private final StrategyInterface strategy;

  /**
   * Constructs an AI player with the specified name, color, and strategy.
   *
   * @param name     the name of the AI player
   * @param color    the color representing the AI player's identity
   * @param strategy the strategy that the AI player will use to make moves
   */
  public AiPlayer(String name, PlayerColor color, StrategyInterface strategy) {
    this.name = name;
    this.color = color;
    this.strategy = strategy;
    this.hand = new ArrayList<>();
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

  @Override
  public String getColor() {
    return color.toString();
  }

  @Override
  public List<CardInterface> getHand() {
    return new ArrayList<>(hand); // Return a copy to prevent outside modification.
  }

  @Override
  public void addCardToHand(CardInterface card) {
    hand.add(card);
  }

  @Override
  public void removeCardFromHand(CardInterface card) {
    hand.remove(card);
  }

  @Override
  public void setHand(List<CardInterface> playerHand) {
    if (playerHand == null) {
      throw new IllegalArgumentException("Hand cannot be null.");
    }
    this.hand.clear();
    this.hand.addAll(playerHand);
  }


  @Override
  public void placeTheCard(CardInterface card, int row, int col) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    }
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Row and column must be non-negative.");
    }

    Moves bestMove = determineNextMove();
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
  public Moves determineNextMove() {
    Moves bestMove = strategy.getBestMove(this);
    if (bestMove == null || bestMove.getRow() < 0 || bestMove.getCol() < 0) {
      throw new IllegalStateException("No valid move available for AI player.");
    }
    return bestMove;
  }


}
