package tripleTrios.model;

public class GameModelImpl implements GameModel{
  private GameModelImpl() {
  }

  @Override
  public void placeCard(Card card, int row, int col) {

  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Player getWinner() {
    return null;
  }

  @Override
  public Grid getGameGrid() {
    return null;
  }

  @Override
  public Player getCurPlayer() {
    return null;
  }

  @Override
  public void nextTurn() {

  }

  @Override
  public void battles(int row, int col) {

  }
}
