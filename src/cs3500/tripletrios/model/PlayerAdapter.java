package cs3500.tripletrios.model;

import cs3500.tripletrios.provider.controller.Actions;
import cs3500.tripletrios.provider.model.PlayerColor;
import cs3500.tripletrios.provider.model.ThreeTriosModelInterface;

/**
 * Adapter class to adapt an IPlayer instance to the Player interface.
 */
public class PlayerAdapter implements cs3500.tripletrios.provider.controller.players.Player {

  private final IPlayer adaptedPlayer;

  /**
   * Constructs a PlayerAdapter.
   *
   * @param player the IPlayer instance to adapt.
   */
  public PlayerAdapter(IPlayer player) {
    this.adaptedPlayer = player;
  }

  @Override
  public void callbackFeatures(Actions features) {
    // Do nothing not needed
  }

  @Override
  public void getMakePlay(ThreeTriosModelInterface model) {
    // doing nothing not needed for this assingment. 

  }

  @Override
  public boolean isHuman() {
    // Assume AI players have a strategy set in IPlayer
    return adaptedPlayer.determineNextMove() == null;
  }

  @Override
  public PlayerColor getColor() {
    return PlayerColor.BLUE;
  }
}
