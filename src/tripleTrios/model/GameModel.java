package tripleTrios.model;

public interface GameModel {

  void placeCard(Card card, int row, int col);

  boolean isGameOver();

  Player getWinner();

  Grid getGameGrid();

  Player getCurPlayer();

  void nextTurn();

  void battles(int row, int col);

}
