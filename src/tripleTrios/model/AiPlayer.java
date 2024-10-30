package tripleTrios.model;

import java.util.List;

/**
 * Represents an AI player in the game.
 * Implements the IPlayer interface.
 */
public class AiPlayer implements IPlayer{

  @Override
  public String getName() {
    return "";
  }

  @Override
  public String getColor() {
    return "";
  }

  @Override
  public List<Card> getHand() {
    return List.of();
  }

  @Override
  public void addCardToHand(Card card) {

  }

  @Override
  public void removeCardFromHand(Card card) {

  }

  @Override
  public void setHand(List<Card> playerHand) {

  }

  @Override
  public void placeTheCard(Card card, int row, int col) {

  }
}
