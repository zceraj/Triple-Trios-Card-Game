package cs3500.threetrios;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

import cs3500.tripletrios.model.Card;

public class ThreeTriosView extends JFrame {
  private final ReadonlyThreeTriosModel model;
  private final JPanel gridPanel;
  private final JPanel handPanel;
  private Card selectedCard;
  private int selectedCardIndex = -1;

  public ThreeTriosView(ReadonlyThreeTriosModel model) {
    this.model = model;
    this.gridPanel = new JPanel(new GridLayout(model.getGridSize(), model.getGridSize()));
    this.handPanel = new JPanel(new FlowLayout());
    this.selectedCard = null;

    setTitle("Three Trios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setSize(800, 600);

    add(gridPanel, BorderLayout.CENTER);
    add(handPanel, BorderLayout.SOUTH);

    initializeHand();
    initializeGrid();

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
      }
    });
  }

  private void initializeGrid() {
    gridPanel.removeAll();
    for (int i = 0; i < model.getGridSize(); i++) {
      for (int j = 0; j < model.getGridSize(); j++) {
        GridCell cell = new GridCell(i, j);
        gridPanel.add(cell);
      }
    }
  }

  private void initializeHand() {
    handPanel.removeAll();
    List<Card> playerHand = model.getPlayerHand(0);  // Example for Player 1
    for (int i = 0; i < playerHand.size(); i++) {
      CardPanel cardPanel = new CardPanel(playerHand.get(i), i);
      handPanel.add(cardPanel);
    }
  }

  private void handleMouseClick(MouseEvent e) {
    // Handle clicks for selecting cards or selecting grid cells
    Component clickedComponent = e.getComponent();
    if (clickedComponent instanceof CardPanel) {
      CardPanel cardPanel = (CardPanel) clickedComponent;
      selectedCardIndex = cardPanel.getIndex();
      selectedCard = cardPanel.getCard();
      System.out.println("Selected card: " + selectedCard);
      highlightSelectedCard(cardPanel);
    } else if (clickedComponent instanceof GridCell) {
      GridCell gridCell = (GridCell) clickedComponent;
      System.out.println("Grid cell clicked: (" + gridCell.getRow() + ", " + gridCell.getColumn() + ")");
    }
  }

  private void highlightSelectedCard(CardPanel cardPanel) {
    // Highlight the selected card with a border or visual effect
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }

  // Internal class for grid cells
  private class GridCell extends JPanel {
    private final int row;
    private final int column;

    public GridCell(int row, int column) {
      this.row = row;
      this.column = column;
      setPreferredSize(new Dimension(60, 60));
      setBorder(BorderFactory.createLineBorder(Color.BLACK));
      setBackground(Color.WHITE);
    }

    public int getRow() {
      return row;
    }

    public int getColumn() {
      return column;
    }
  }

  // Internal class for player cards in hand
  private class CardPanel extends JPanel {
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
}
