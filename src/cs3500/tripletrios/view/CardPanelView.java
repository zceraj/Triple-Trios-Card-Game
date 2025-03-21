package cs3500.tripletrios.view;

import cs3500.tripletrios.model.CardInterface;

/**
 * a card panel for the grid and hand in the gui view of the ttt game.
 */

public interface CardPanelView {
  /**
   * Returns the card contained in this panel.
   *
   * @return The card in this panel.
   */
  CardInterface getCard();

}

