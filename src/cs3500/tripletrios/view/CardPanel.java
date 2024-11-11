
package cs3500.tripletrios.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;


import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.Direction;

/**
 * a card that appears on the side of the board before its played.
 */
class CardPanel extends JPanel {
  private final Card card;
  private final int index;
  private boolean isSelected = false;

  /**
   * setting up my cards in the gui!!
   * @param card the card being portrayed --> obvi a need because how else will it access
   *             all of the card's values.
   * @param color the color of the card --> to know which players side is which.
   * @param index for future calling of the card.
   */
  public CardPanel(Card card, Color color, int index) {
    this.card = card;
    this.index = index;

    // size of each card yay
    setPreferredSize(new Dimension(100, 140));
    // to be able to have the color just set by the players side
    setBackground(color);

    setLayout(new BorderLayout());

    // labels with attack values or the card name as the text shown
    JLabel nameLabel = new JLabel(card.getCardName(), SwingConstants.CENTER);
    JLabel northLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.NORTH)), SwingConstants.CENTER);
    JLabel eastLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.EAST)), SwingConstants.CENTER);
    JLabel southLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.SOUTH)), SwingConstants.CENTER);
    JLabel westLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.WEST)), SwingConstants.CENTER);


    try {
      Font customFontForLabel = Font.createFont(Font.TRUETYPE_FONT, new File("sillyStuff/SourGummy-VariableFont_wdth,wght.ttf")).deriveFont(14f);
      Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("sillyStuff/SourGummy-VariableFont_wdth,wght.ttf")).deriveFont(23f);
      nameLabel.setFont(customFontForLabel);
      northLabel.setFont(customFont);
      eastLabel.setFont(customFont);
      southLabel.setFont(customFont);
      westLabel.setFont(customFont);
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();  // Print the exception to understand what went wrong
    }



    // putting all my things in the right spot
    add(northLabel, BorderLayout.NORTH);
    add(eastLabel, BorderLayout.EAST);
    add(southLabel, BorderLayout.SOUTH);
    add(westLabel, BorderLayout.WEST);
    add(nameLabel, BorderLayout.CENTER);

    // i didnt like how smushed they looked, so i added some padding on each border
    northLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    eastLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    southLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    westLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    // black borders to distinguish from the board
    setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Mouse listener for card selection
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick();
      }
    });
  }



  /**
   * to be able to use the values.
   * @return the card in the box.
   */
  public Card getCard() {
    return card;
  }

  /**
   * to play the card.
   * @return the index of the box
   */
  public int getIndex() {
    return index;
  }

  // Handle card selection/deselection
  private void handleCardClick() {
    if (isSelected) {
      // Deselect the card
      isSelected = false;
      setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Reset border to default
      System.out.println("Deselected card: " + card.getCardName() + " (Index: " + index + ")");
    } else {
      // Select the card
      this.isSelected = true;
      setBorder(BorderFactory.createLineBorder(Color.GRAY, 5)); // Highlight with gray border
      System.out.println("Selected card: " + card.getCardName() + " (Index: " + index + ")");
    }
  }

  // Optional: Custom paint method to enhance visual representation (e.g., highlight or show card name)
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (isSelected) {
      g.setColor(Color.GRAY); // Change this if you want a different visual effect for selection
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  }
}