package cs3500.tripletrios.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JFrame;


import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Direction;

/**
 * a card that appears on the side of the board before its played.
 */
class CardPanel extends JPanel implements CardPanelView {
  private final CardInterface card;
  private final boolean isSelected = false;
  private TripleTrioGuiView view;

  /**
   * setting up my cards in the gui!.
   *
   * @param card  the card being portrayed --> obvi a need because how else will it access
   *              all of the card's values.
   * @param color the color of the card --> to know which players side is which.
   */
  public CardPanel(CardInterface card, Color color, GameViewGUI view, JFrame parent) {
    this.card = card;

    // size of each card yay
    setPreferredSize(new Dimension(parent.getWidth() / 15, parent.getHeight() / 8));
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
              .deriveFont(8000f / parent.getHeight());
      Font customFont = Font.createFont(
                      Font.TRUETYPE_FONT,
                      new File(
                              "formatting/SourGummy-VariableFont_wdth,wght.ttf"))
              .deriveFont(10000f / parent.getHeight());
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
            100 / parent.getHeight(), 0, 0, 0));
    eastLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 200 / parent.getHeight(), 0, 200 / parent.getHeight()));
    southLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 0, 200 / parent.getHeight(), 0));
    westLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 200 / parent.getHeight(), 0, 200 / parent.getHeight()));

    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }


  private void setText() {
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
              .deriveFont(8000f / view.getHeight());
      Font customFont = Font.createFont(
                      Font.TRUETYPE_FONT,
                      new File(
                              "formatting/SourGummy-VariableFont_wdth,wght.ttf"))
              .deriveFont(10000f / view.getHeight());
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
            100 / view.getHeight(), 0, 0, 0));
    eastLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 200 / view.getHeight(), 0, 200 / view.getHeight()));
    southLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 0, 200 / view.getHeight(), 0));
    westLabel.setBorder(BorderFactory.createEmptyBorder(
            0, 200 / view.getHeight(), 0, 200 / view.getHeight()));

  }

  /**
   * to be able to use the values.
   *
   * @return the card in the box.
   */
  @Override
  public CardInterface getCard() {
    return card;
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    int width = getWidth();
    int height = getHeight();
    if (isSelected) {
      g.setColor(Color.GRAY);
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  }
}