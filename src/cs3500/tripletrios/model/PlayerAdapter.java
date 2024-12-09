package cs3500.tripletrios.model;

import cs3500.tripletrios.provider.controller.Actions;
import cs3500.tripletrios.provider.model.PlayerColor;
import cs3500.tripletrios.provider.model.ThreeTriosModelInterface;

/**
 * Adapter class to adapt an IPlayer instance to the Player interface.
 */
public class PlayerAdapter implements cs3500.tripletrios.provider.controller.players.Player {

  private final IPlayer adaptedPlayer;
  private Actions actions;

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
    
    this.actions = features;
  }

  @Override
  public void getMakePlay(ThreeTriosModelInterface model) {

    if (!isHuman() && adaptedPlayer.determineNextMove() != null) {

    }

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
