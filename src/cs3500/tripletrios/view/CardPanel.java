package cs3500.tripletrios.view;

import java.awt.*;

import javax.swing.*;

import cs3500.tripletrios.model.Card;

class CardPanel extends JPanel {
  private final Card card;
  private final int index;

  public CardPanel(Card card, int index) {
    this.card = card;
    this.index = index;
    setPreferredSize(new Dimension(80, 120));
    setBackground(Color.LIGHT_GRAY);
    add(new JLabel(card.getCardName()));
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  public Card getCard() {
    return card;
  }

  public int getIndex() {
    return index;
  }
}