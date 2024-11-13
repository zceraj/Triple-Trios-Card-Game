package cs3500.tripletrios.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;

/**
 * Represents a graphical component in a grid-based view for the Triple Trios game.
 * Each instance displays a cell within the game grid and is responsible for rendering
 * the cell's appearance, including any card within the cell. It also handles mouse click
 * events on the cell.
 */
public class GridPanel extends JPanel implements GridCellView {
  private final Cell cell;
  private final int row;
  private final int col;

  /**
   * Constructs a GridPanel instance representing a specific cell in the grid.
   * Configures the panel size, background color based on cell type, and adds a mouse listener.
   *
   * @param cell the Cell this panel represents
   * @param row  the row index of the cell in the grid
   * @param col  the column index of the cell in the grid
   */
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

  /**
   * Handles the click event for this grid cell.
   * Prints the cell's coordinates and card name (if present) and updates the cell's border.
   */
  public void handleGridCellClick() {
    // Print the coordinates of the clicked cell starting at 0 with the rows going down
    // so for a grid "xox" o would be (0,1) bc its the first row and the second column
    System.out.println("Grid cell clicked: (" + row + ", " + col + ")");

    if (cell.isCardCell()) {
      if (cell.getCard() != null) {
        System.out.println("Card in this cell: " + cell.getCard().getCardName());
      }

      setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150), 5));
      repaint();
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
      int x = (getWidth() - fm.stringWidth(cardName)) / 2;
      int y = (getHeight() + fm.getAscent()) / 2;
      g.drawString(cardName, x, y);
    }
  }

  /**
   * gets the row of the cell.
   * @return the row as an int starting at 0.
   */
  @Override
  public int getRow() {
    return row;
  }

  /**
   * gets the column of the cell.
   * @return the column as an int starting at 0.
   */
  @Override
  public int getCol() {
    return col;
  }
}

