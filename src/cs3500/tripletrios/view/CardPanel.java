package cs3500.tripletrios.view;

import java.awt.*;

import javax.swing.*;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.Direction;

class CardPanel extends JPanel {
  private final Card card;
  private final int index;

  public CardPanel(Card card, Color color, int index) {
    this.card = card;
    this.index = index;
    setPreferredSize(new Dimension(80, 120));
    setBackground(color);
    add(new JLabel(card.getCardName()));
    add(new JLabel(card.getAttackValue(Direction.NORTH)));
    add(new JLabel(card.getAttackValue(Direction.EAST)));
    add(new JLabel(card.getAttackValue(Direction.SOUTH)));
    add(new JLabel(card.getAttackValue(Direction.WEST)));

    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  public Card getCard() {
    return card;
  }

  public int getIndex() {
    return index;
  }
}