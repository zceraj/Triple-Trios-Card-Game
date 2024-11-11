package cs3500.tripletrios.view;

import java.awt.*;

import cs3500.tripletrios.model.Card;

public interface CardPanelView {
    /**
     * Initializes the visual properties of the card panel (e.g., background color, font).
     */
    void initializeCardPanel();

    /**
     * Handles the selection or deselection of the card.
     * Changes visual representation and internal state accordingly.
     */
    void handleCardClick();

    /**
     * Returns the card contained in this panel.
     * @return The card in this panel.
     */
    Card getCard();

    /**
     * Returns the index of this card panel for referencing or playing the card.
     * @return The index of the card panel.
     */
    int getIndex();

    /**
     * Custom rendering for the card panel, such as highlighting the card or other visual effects.
     * @param g The graphics context for painting the panel.
     */
    void renderCardPanel(Graphics g);

  }

