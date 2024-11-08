package cs3500.tripletrios.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.ReadOnlyGameModel;

public class TripleTrioGuiView extends JFrame {
  private final ReadOnlyGameModel model;
  private final JPanel gridPanel;
  private final JPanel handPanel;
  private Card selectedCard;
  private int selectedCardIndex = -1;

  public TripleTrioGuiView(ReadOnlyGameModel model) {
    this.model = model;
    this.gridPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), model.getGameGrid().getCols()));
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
    for (int rows = 0; rows < model.getGameGrid().getRows(); rows++) {
      for (int cols = 0; cols < model.getGameGrid().getCols(); cols++) {
        GridCell cell = new GridCell(rows, cols);
        gridPanel.add(cell);
      }
    }
  }

  private void initializeHand() {
    handPanel.removeAll();
    List<Card> playerHand = model.getCurPlayer().getHand();
    for (int i = 0; i < playerHand.size(); i++) {
      CardPanel cardPanel = new CardPanel(playerHand.get(i), i);
      handPanel.add(cardPanel);
    }
  }

  private void handleMouseClick(MouseEvent e) {
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
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }

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

}
