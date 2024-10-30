package tripleTrios.model;

import java.util.List;

public interface IPlayer {
  String getName();
  String getColor();
  List<Card> getHand();
  void addCardToHand(Card card);
  void removeCardFromHand(Card card);

}
