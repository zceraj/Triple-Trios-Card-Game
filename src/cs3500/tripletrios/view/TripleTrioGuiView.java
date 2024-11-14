package cs3500.tripletrios.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.BorderFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.ReadOnlyGameModel;

/**
 * Represents the graphical user interface (GUI) view for the "Triple Trio" gam.
 * This class extends JFrame and is responsible for rendering
 * the game board, players' hands, and handling card selection and interaction.
 * The view updates dynamically based on the game state provided by a model.
 */
public class TripleTrioGuiView extends JFrame implements GameViewGUI {
  private final ReadOnlyGameModel model;
  private final JPanel gridPanel;
  private final JPanel leftColumnPanel;
  private final JPanel rightColumnPanel;
  private CardPanel selectedCard;
  private int selectedCardIndex = -1;

  /**
   * Initializes the frame
   * and layout, creates panels for the game grid and player hands, and calls initialization
   * methods to populate the view based on the current game state.
   *
   * @param model the read-only model representing the current game state.
   */
  public TripleTrioGuiView(ReadOnlyGameModel model) {
    this.model = model;
    this.gridPanel = new JPanel(
            new GridLayout(model.getGameGrid().getRows(), model.getGameGrid().getCols()));
    this.leftColumnPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), 1));
    this.rightColumnPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), 1));
    this.selectedCard = null;

    setTitle("Three Trios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setSize(800, 600);
    this.setResizable(true);

    add(gridPanel, BorderLayout.CENTER);
    add(leftColumnPanel, BorderLayout.WEST);
    add(rightColumnPanel, BorderLayout.EAST);

    initializeGrid();
    initializeHands();

    repaint();
  }

  /**
   * Initializes the game grid based on the model's current game state, creating a grid of
   * GricCell instances representing each cell in the game. This method updates the grid panel to
   * display each cell as specified by the model.
   */
  private void initializeGrid() {
    gridPanel.removeAll();
    List<List<Cell>> cells = new ArrayList<>();
    // Retrieve the cells and add them to the panel
    for (int row = 0; row < model.getGameGrid().getRows(); row++) {
      List<Cell> rowCells = new ArrayList<>();
      for (int col = 0; col < model.getGameGrid().getCols(); col++) {
        Cell cell = model.getGameGrid().getCell(row, col);
        GridPanel gridCellPanel = new GridPanel(cell, row, col, model);

        gridPanel.add(gridCellPanel);
        rowCells.add(cell);
      }
      cells.add(rowCells);
    }

    gridPanel.revalidate();
    gridPanel.repaint();
  }

  /**
   * Initializes the players' hands, creating a column of CardPanels for each player,
   * color-coded to represent the player.
   */
  private void initializeHands() {
    leftColumnPanel.removeAll();
    rightColumnPanel.removeAll();
    List<CardInterface> leftColumnCards = model.getCurPlayer().getHand();
    List<CardInterface> rightColumnCards = model.getOtherPlayer().getHand();
    Color leftColumnCardsColor;
    Color rightColumnCardsColor;

    Color red = new Color(200, 50, 100);
    Color blue = new Color(50, 100, 200);

    if (model.getCurPlayer().getColor().equals("BLUE")) {
      leftColumnCardsColor = blue;
      rightColumnCardsColor = red;
    } else {
      leftColumnCardsColor = red;
      rightColumnCardsColor = blue;
    }

    for (int i = 0; i < leftColumnCards.size(); i++) {
      CardPanel cardPanel = new CardPanel(leftColumnCards.get(i), leftColumnCardsColor, i, this);
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(cardPanel);
        }
      });
      leftColumnPanel.add(cardPanel);
    }

    for (int i = 0; i < rightColumnCards.size(); i++) {
      CardPanel cardPanel = new CardPanel(rightColumnCards.get(i), rightColumnCardsColor, i, this);
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(cardPanel);
        }
      });
      rightColumnPanel.add(cardPanel);
    }
  }

  /**
   * Handles the event of a card being clicked by the player, managing the selection and
   * highlighting of the card. If a card is already selected, clicking on it again will
   * deselect it. Otherwise, the newly selected card is highlighted.
   * All details are printed to the console.
   *
   * @param cardPanel the card panel that was clicked
   */
  private void handleCardClick(CardPanel cardPanel) {
    // If the same card is clicked again, deselect it
    if (selectedCard == cardPanel.getCard()) {
      selectedCard = null;
      selectedCardIndex = -1;
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Reset border
      System.out.println("Deselected card.");
    }
    if (selectedCard != cardPanel.getCard() && selectedCardIndex != -1 && selectedCard != null) {
      cardPanel.setBorder(BorderFactory.createEmptyBorder());
      selectedCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
      System.out.println("Deselected card.");
      selectedCard = null;
      selectedCardIndex = -1;
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    } else {
      // If a different card is clicked, highlight it
      if (selectedCard != null) {
        // Reset the border of the previously selected card
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      }

      selectedCard = cardPanel;
      selectedCardIndex = cardPanel.getIndex();
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5)); // Highlight border

      IPlayer playerOwner = model.getPlayerFromCard(cardPanel.getCard());

      System.out.println("Selected card: "
              + selectedCard.getCard().getCardName()
              + " (Index: " + selectedCardIndex + " owned by "
              + playerOwner.getName() + ")");
    }
  }

  /**
   * Sets the visibility of the gui frame.
   *
   * @param visible true or false to hide (false) or show (true).
   */
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }

  /**
   * renders the view.
   *
   * @throws IOException I don't know why it would throw an exception
   */
  @Override
  public void render() throws IOException {
    setVisible(true);
  }

  /**
   * Refreshes the display of the player's hands in the game view.
   * This method should be called whenever the player's hand is updated.
   */
  public void refreshHands() {
    initializeHands();
    leftColumnPanel.revalidate();
    leftColumnPanel.repaint();
    rightColumnPanel.revalidate();
    rightColumnPanel.repaint();
  }

  /**
   * Refreshes the game grid in the GUI.
   * This method should be called whenever the grid layout or state changes,
   * such as after a card is placed or the game progresses.
   */
  public void refreshGrid() {
    // Iterate through all components in the gridPanel container
    for (Component component : gridPanel.getComponents()) {
      // Check if the component is an instance of GridPanel
      if (component instanceof GridPanel) {
        GridPanel gridPanelComponent = (GridPanel) component;
        // Revalidate and update the color for each GridPanel
        gridPanelComponent.revalidate();
        gridPanelComponent.repaintGrid(model);
      }
    }
    // Refresh the gridPanel to reflect changes
    gridPanel.revalidate();
    gridPanel.repaint();
  }


}
