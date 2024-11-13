package cs3500.tripletrios.view;

import java.util.List;

import cs3500.tripletrios.model.CardInterface;

/**
 * an interface for a game view that pops up a new window to play the game.
 */
public interface GameViewGUI extends GameView {
  void setVisible(boolean visible);
}
