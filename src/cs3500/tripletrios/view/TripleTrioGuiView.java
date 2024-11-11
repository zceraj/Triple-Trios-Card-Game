package cs3500.tripletrios.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.Cell;
import cs3500.tripletrios.model.ReadOnlyGameModel;

public class TripleTrioGuiView extends JFrame{
  private final ReadOnlyGameModel model;
  private final JPanel gridPanel;
  private final JPanel leftColumnPanel;
  private final JPanel rightColumnPanel;
  private Card selectedCard;
  private int selectedCardIndex = -1;


  public TripleTrioGuiView(ReadOnlyGameModel model) {
    this.model = model;
    this.gridPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), model.getGameGrid().getCols()));
    this.leftColumnPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), 1));
    this.rightColumnPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), 1));
    this.selectedCard = null;

    setTitle("Three Trios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setSize(800, 600);

    add(gridPanel, BorderLayout.CENTER);
    add(leftColumnPanel, BorderLayout.WEST);
    add(rightColumnPanel, BorderLayout.EAST);

    initializeGrid();
    initializeHands();

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
      }
    });
  }

  private void initializeGrid() {
    gridPanel.removeAll();
    List<List<Cell>> cells = new ArrayList<>();

    for (int row = 0; row < model.getGameGrid().getRows(); row++) {
      List<Cell> rowCells = new ArrayList<>();
      for (int col = 0; col < model.getGameGrid().getCols(); col++) {
        rowCells.add(model.getGameGrid().getCell(row, col));
      }
      cells.add(rowCells);
    }

    for (int row = 0; row < cells.size(); row++) {
      for (int col = 0; col < cells.get(row).size(); col++) {
        Cell cell = cells.get(row).get(col);
        GridPanel gridCellPanel = new GridPanel(cell, row, col);
        gridPanel.add(gridCellPanel);
      }
    }

    gridPanel.revalidate();
    gridPanel.repaint();
  }


  private void initializeHands() {
    leftColumnPanel.removeAll();
    rightColumnPanel.removeAll();
    List<Card> leftColumnCards = model.getCurPlayer().getHand();
    List<Card> rightColumnCards = model.getOtherPlayer().getHand();
    Color leftColumnCardsColor;
    Color rightColumnCardsColor;

    Color red = new Color(200, 50, 100);
    Color blue = new Color(50, 100, 200);

    if (model.getCurPlayer().getColor() == "BLUE") {
      leftColumnCardsColor = blue;
      rightColumnCardsColor = red;
    }
    else {
      leftColumnCardsColor = red;
      rightColumnCardsColor = blue;
    }

   for (int i = 0; i < leftColumnCards.size(); i++) {
    CardPanel cardPanel = new CardPanel(leftColumnCards.get(i), leftColumnCardsColor, i);
    cardPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(cardPanel);
      }
    });
    leftColumnPanel.add(cardPanel);
  }

    for (int i = 0; i < rightColumnCards.size(); i++) {
    CardPanel cardPanel = new CardPanel(rightColumnCards.get(i), rightColumnCardsColor, i);
    cardPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(cardPanel);
      }
    });
    rightColumnPanel.add(cardPanel);
  }
}

private void handleCardClick(CardPanel cardPanel) {
  // If the same card is clicked again, deselect it
  if (selectedCard == cardPanel.getCard()) {
    selectedCard = null;
    selectedCardIndex = -1;
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Reset border
    System.out.println("Deselected card.");
  } else {
    // If a different card is clicked, highlight it
    if (selectedCard != null) {
      // Reset the border of the previously selected card
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    selectedCard = cardPanel.getCard();
    selectedCardIndex = cardPanel.getIndex();
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5)); // Highlight border
    System.out.println("Selected card: " + selectedCard.getCardName() + " (Index: " + selectedCardIndex + ")");
  }
}

private void handleMouseClick(MouseEvent e) {
  Component clickedComponent = e.getComponent();
  if (clickedComponent instanceof GridCell) {
    GridCell gridCell = (GridCell) clickedComponent;
    System.out.println("Grid cell clicked: (" + Integer.toString(gridCell.row) + ", " + Integer.toString(gridCell.row) + ")");
  }
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
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
      }
    });
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
      System.out.println("Grid cell clicked: (" + Integer.toString(gridCell.row) + ", " + Integer.toString(gridCell.row) + ")");
    }
  }

  private void highlightSelectedCard(CardPanel cardPanel) {
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }
}
}
