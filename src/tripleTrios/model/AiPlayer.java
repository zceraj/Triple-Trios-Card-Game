package tripleTrios.model;

import java.util.List;

public class AiPlayer implements IPlayer{
  @Override
  public String getName() {
    //will return the name of the player
    return "AI";
  }

  @Override
  public String getColor() {
    //will be the opposite color of the human player
    return "";
  }

  @Override
  public List<Card> getHand() {
    //will return the hand of the AI player
    return List.of();
  }

  @Override
  public void addCardToHand(Card card) {
    //adds the cards to AI player hand
  }

  @Override
  public void removeCardFromHand(Card card) {
    //removes played cards from AI player hand
  }

  @Override
  public void setHand(List<Card> playerHand) {
    //sets the hand of the AI player
  }
}
