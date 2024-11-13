package cs3500.tripletrios.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;


import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Direction;

/**
 * a card that appears on the side of the board before its played.
 */
class CardPanel extends JPanel implements CardPanelView {
  private final CardInterface card;
  private final int index;
  private final boolean isSelected = false;
  private final Dimension CHANGING_SIZE = new Dimension(100, 140);

  /**
   * setting up my cards in the gui!!
   * @param card the card being portrayed --> obvi a need because how else will it access
   *             all of the card's values.
   * @param color the color of the card --> to know which players side is which.
   * @param index for future calling of the card.
   */
  public CardPanel(CardInterface card, Color color, int index) {
    this.card = card;
    this.index = index;

    // size of each card yay
    setPreferredSize(CHANGING_SIZE);
    // to be able to have the color just set by the players side
    setBackground(color);

    setLayout(new BorderLayout());

    // labels with attack values or the card name as the text shown
    JLabel nameLabel = new JLabel(card.getCardName(), SwingConstants.CENTER);
    JLabel northLabel = new JLabel(
            String.valueOf(card.getAttackValue(Direction.NORTH)), SwingConstants.CENTER);
    JLabel eastLabel = new JLabel(
            String.valueOf(card.getAttackValue(Direction.EAST)), SwingConstants.CENTER);
    JLabel southLabel = new JLabel(
            String.valueOf(card.getAttackValue(Direction.SOUTH)), SwingConstants.CENTER);
    JLabel westLabel = new JLabel(
            String.valueOf(card.getAttackValue(Direction.WEST)), SwingConstants.CENTER);


    try {
      Font customFontForLabel = Font.createFont(
              Font.TRUETYPE_FONT,
              new File(
                      "formatting/SourGummy-VariableFont_wdth,wght.ttf"))
              .deriveFont(2000f/ CHANGING_SIZE.height);
      Font customFont = Font.createFont(
              Font.TRUETYPE_FONT,
              new File(
                      "formatting/SourGummy-VariableFont_wdth,wght.ttf"))
              .deriveFont(3000f/ CHANGING_SIZE.height);
      nameLabel.setFont(customFontForLabel);
      northLabel.setFont(customFont);
      eastLabel.setFont(customFont);
      southLabel.setFont(customFont);
      westLabel.setFont(customFont);
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }

    add(northLabel, BorderLayout.NORTH);
    add(eastLabel, BorderLayout.EAST);
    add(southLabel, BorderLayout.SOUTH);
    add(westLabel, BorderLayout.WEST);
    add(nameLabel, BorderLayout.CENTER);

    northLabel.setBorder(BorderFactory.createEmptyBorder(
            200/ CHANGING_SIZE.height, 0, 0, 0));
    eastLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 200/ CHANGING_SIZE.width, 0, 200/ CHANGING_SIZE.width));
    southLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 0, 200/ CHANGING_SIZE.height, 0));
    westLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 200/ CHANGING_SIZE.width, 0, 200/ CHANGING_SIZE.width));

    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }


  /**
   * to be able to use the values.
   * @return the card in the box.
   */
  @Override
  public CardInterface getCard() {
    return card;
  }

  /**
   * to play the card.
   * @return the index of the box
   */
  @Override
  public int getIndex() {
    return index;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (isSelected) {
      g.setColor(Color.GRAY);
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  }

  @Override
  public Dimension getPreferredSize() {
    Container parent = getParent();
    if (parent != null) {
      int width = (int) (parent.getWidth() * 0.2);
      int height = (int) (parent.getHeight() * 0.3);
      return new Dimension(Math.max(width, CHANGING_SIZE.width), Math.max(height, CHANGING_SIZE.height));
    }
    return CHANGING_SIZE;
  }

}