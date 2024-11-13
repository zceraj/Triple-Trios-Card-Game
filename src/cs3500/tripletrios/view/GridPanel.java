package cs3500.tripletrios.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;

public class GridPanel extends JPanel implements GridCellView {
  private final Cell cell;
  private final int row;
  private final int col;

  public GridPanel(Cell cell, int row, int col) {
    this.cell = cell;
    this.row = row;
    this.col = col;
    setPreferredSize(new Dimension(80, 120));
    if (cell.isCardCell()) {
      setBackground(new Color(200, 150, 255));
    }
    else {
      setBackground(Color.LIGHT_GRAY);
    }
    setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150)));

    // Add mouse listener to handle clicks on the grid cell
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleGridCellClick();
      }
    });
  }

  public CardInterface getCard() {
    return cell.getCard();
  }

  // Handle click event on the grid cell
  public void handleGridCellClick() {
    // Print the coordinates of the clicked cell starting at 0 with the rows going down
    // so for a grid "xox" o would be (0,1) bc its the first row and the second column
    System.out.println("Grid cell clicked: (" + row + ", " + col + ")");

    if (cell.isCardCell()) {
      if (cell.getCard() != null) {
        System.out.println("Card in this cell: " + cell.getCard().getCardName());
      }

      // Highlight the grid cell visually (e.g., changing the border color on click)
      setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150), 5)); // Example of highlighting
      repaint(); // Ensure the highlight is painted
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // If the cell contains a card, display the card name in the center
    if (cell.isCardCell() && cell.getCard() != null) {
      CardInterface card = cell.getCard();
      g.setColor(Color.BLACK); // Set text color
      g.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for card name
      String cardName = card.getCardName();

      // Draw the card name in the center of the cell
      FontMetrics fm = g.getFontMetrics();
      int x = (getWidth() - fm.stringWidth(cardName)) / 2; // Center horizontally
      int y = (getHeight() + fm.getAscent()) / 2; // Center vertically
      g.drawString(cardName, x, y);
    }
  }

  @Override
  public int getRow(){
    return row;
  }

  @Override
  public int getCol(){
    return col;
  }
}

