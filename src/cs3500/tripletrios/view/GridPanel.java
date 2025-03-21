package cs3500.tripletrios.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.CellInterface;
import cs3500.tripletrios.model.Direction;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

import static cs3500.tripletrios.model.PlayerColor.BLUE;
import static cs3500.tripletrios.model.PlayerColor.RED;

/**
 * Represents a graphical component in a grid-based view for the Triple Trios game.
 * Each instance displays a cell within the game grid and is responsible for rendering
 * the cell's appearance, including any card within the cell. It also handles mouse click
 * events on the cell.
 */
public class GridPanel extends JPanel implements GridCellView {
  private CellInterface cell;
  private final int row;
  private final int col;
  private final ReadOnlyGameModel model;
  private final IPlayer player;
  private final GameViewGUI gameView;

  /**
   * Constructs a GridPanel instance representing a specific cell in the grid.
   * Configures the panel size, background color based on cell type, and adds a mouse listener.
   *
   * @param cell the Cell this panel represents
   * @param row  the row index of the cell in the grid
   * @param col  the column index of the cell in the grid
   */
  public GridPanel(CellInterface cell, int row, int col, ReadOnlyGameModel model,
                   IPlayer player, GameViewGUI gameView) {
    this.cell = cell;
    this.row = row;
    this.col = col;

    this.model = model;
    this.player = player;
    this.gameView = gameView;

    setPreferredSize(new Dimension(80, 120));
    setColor();
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
    if (model.getCurPlayer() == player && gameView.getSelectedPanel() == null) {
      // Print the coordinates of the clicked cell starting at 0 with the rows going down
      // so for a grid "xox" o would be (0,1) bc it's the first row and the second column
      System.out.println("Grid cell clicked: (" + row + ", " + col + ")");

      if (cell.isCardCell()) {
        if (gameView.getSelectedPanel() != null) {
          gameView.getSelectedPanel()
                  .setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150)));
        }
        gameView.setSelectedPanel(this);
        setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150), 5));
        repaint();
      }
    }
    try {
      gameView.notifyObservers();
    } catch (IOException e) {
      gameView.popup("oopsies");
    }
  }

  /**
   * gets the row of the cell.
   *
   * @return the row as an int starting at 0.
   */
  @Override
  public int getRow() {
    return row;
  }

  /**
   * gets the column of the cell.
   *
   * @return the column as an int starting at 0.
   */
  @Override
  public int getCol() {
    return col;
  }

  /**
   * sets the colr of the cell depending on if it is a card cell and if theres a card inside.
   */
  private void setColor() {
    this.removeAll();
    if (cell.isCardCell()) {
      if (cell.getCard() != null) {
        Color cellColor;
        if (model.getPlayerFromCard(cell.getCard()).getColor().equalsIgnoreCase(BLUE.name())) {
          cellColor = new Color(50, 100, 200);
        } else if (model.getPlayerFromCard(cell.getCard()).getColor()
                .equalsIgnoreCase(RED.name())) {
          cellColor = new Color(200, 50, 100);
        } else {
          cellColor = new Color(0, 255, 0);
        }
        setBackground(cellColor);
        setLabel();
      } else {
        setBackground(new Color(200, 150, 255));
      }
    } else {
      setBackground(Color.LIGHT_GRAY);
    }
  }

  /**
   * repaints the grid and changes the color of the cell.
   */
  public void repaintGrid(ReadOnlyGameModel model) {
    this.cell = model.getGameGrid().getCell(this.row, this.col);
    setColor();
    repaint();
  }

  /**
   * sets the label of the card on the cell.
   */
  private void setLabel() {
    JLabel nameLabel = new JLabel(cell.getCard().getCardName(), SwingConstants.CENTER);
    JLabel northLabel = new JLabel(
            String.valueOf(
                    cell.getCard().getAttackValue(Direction.NORTH)), SwingConstants.CENTER);
    JLabel eastLabel = new JLabel(
            String.valueOf(
                    cell.getCard().getAttackValue(Direction.EAST)), SwingConstants.CENTER);
    JLabel southLabel = new JLabel(
            String.valueOf(
                    cell.getCard().getAttackValue(Direction.SOUTH)), SwingConstants.CENTER);
    JLabel westLabel = new JLabel(
            String.valueOf(
                    cell.getCard().getAttackValue(Direction.WEST)), SwingConstants.CENTER);
    try {
      Font customFontForLabel = Font.createFont(
                      Font.TRUETYPE_FONT,
                      new File(
                              "formatting/SourGummy-VariableFont_wdth,wght.ttf"))
              .deriveFont(15f);
      Font customFont = Font.createFont(
                      Font.TRUETYPE_FONT,
                      new File(
                              "formatting/SourGummy-VariableFont_wdth,wght.ttf"))
              .deriveFont(17f);
      nameLabel.setFont(customFontForLabel);
      northLabel.setFont(customFont);
      eastLabel.setFont(customFont);
      southLabel.setFont(customFont);
      westLabel.setFont(customFont);
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
    setLayout(new BorderLayout());
    add(northLabel, BorderLayout.NORTH);
    add(eastLabel, BorderLayout.EAST);
    add(southLabel, BorderLayout.SOUTH);
    add(westLabel, BorderLayout.WEST);
    add(nameLabel, BorderLayout.CENTER);
    northLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    eastLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    southLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    westLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
  }
}

