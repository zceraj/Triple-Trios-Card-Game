package cs3500.tripletrios.view;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.CellInterface;
import cs3500.tripletrios.provider.model.*;
import cs3500.tripletrios.observing.Observer;
import cs3500.tripletrios.provider.view.ThreeTriosGUIView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Adapter to integrate the ThreeTriosGUIViewInterface with the existing GUI structure.
 */
public class ViewAdapter extends JFrame implements GameViewGUI {
  private final ReadOnlyThreeTriosModelInterface model;
  private final PlayerColor player;
  private final JPanel gridPanel;
  private final JPanel leftColumnPanel;
  private final JPanel rightColumnPanel;
  private final List<Observer> observers;
  private final ThreeTriosGUIView view;

  private CardPanel selectedCard;
  private GridPanel selectedPanel;
  private boolean handsInitialized;

  /**
   * Constructs a new adapter for the Three Trios GUI view.
   *
   * @param model  the read-only model of the game.
   * @param player the player for whom the view is rendered.
   */
  public ViewAdapter(ReadOnlyThreeTriosModelInterface model, PlayerColor player, ThreeTriosGUIView view) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model and player cannot be null");
    }
    this.model = model;
    this.view = view;
    this.player = player;
    this.handsInitialized = false;
    this.observers = new ArrayList<>();
    this.gridPanel = new JPanel(new GridLayout(model.getGrid().getRows(), model.getGrid().getCols()));
    this.leftColumnPanel = new JPanel(new GridLayout(model.getPlayerHand(PlayerColor.RED).size(), 1));
    this.rightColumnPanel = new JPanel(new GridLayout(model.getPlayerHand(PlayerColor.BLUE).size(), 1));
    initializeFrame();
    initializeGrid();
    initializeQuitButton();
  }

  private void initializeFrame() {
    setTitle("Three Trios - Player: " + player);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setSize(800, 600);
    setResizable(true);

    add(gridPanel, BorderLayout.CENTER);
    add(leftColumnPanel, BorderLayout.WEST);
    add(rightColumnPanel, BorderLayout.EAST);
  }

  private void initializeGrid() {
    gridPanel.removeAll();
    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        CellInterface cell = model.getGrid().getCell(row, col);
        GridPanel gridCellPanel = new GridPanel(cell, row, col, model, player, this);
        gridPanel.add(gridCellPanel);
      }
    }
    gridPanel.revalidate();
    gridPanel.repaint();
  }

  private void initializeQuitButton() {
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener(e -> System.exit(0));
    add(quitButton, BorderLayout.SOUTH);
  }

  @Override
  public void initializeHands() {
    leftColumnPanel.removeAll();
    rightColumnPanel.removeAll();
    List<CardInterface> leftHand = model.getPlayerHand(PlayerColor.RED);
    List<CardInterface> rightHand = model.getPlayerHand(PlayerColor.BLUE);

    createCardPanels(leftColumnPanel, leftHand, PlayerColor.RED);
    createCardPanels(rightColumnPanel, rightHand, PlayerColor.BLUE);

    handsInitialized = true;
    repaint();
  }

  private void createCardPanels(JPanel panel, List<CardInterface> cards, PlayerColor color) {
    Color panelColor = (color == PlayerColor.BLUE) ? new Color(50, 100, 200) : new Color(200, 50, 100);
    for (CardInterface card : cards) {
      CardPanel cardPanel = new CardPanel(card, panelColor, this);
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(cardPanel);
        }
      });
      panel.add(cardPanel);
    }
  }

  private void handleCardClick(CardPanel cardPanel) {
    if (selectedCard == cardPanel) {
      deselectCard();
    } else {
      selectCard(cardPanel);
    }
    notifyObservers();
  }

  private void deselectCard() {
    if (selectedCard != null) {
      selectedCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      selectedCard = null;
    }
  }

  private void selectCard(CardPanel cardPanel) {
    deselectCard();
    selectedCard = cardPanel;
    selectedCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
  }

  @Override
  public void render() {
    setVisible(true);
  }

  @Override
  public void refreshGrid() {
    for (Component component : gridPanel.getComponents()) {
      if (component instanceof GridPanel) {
        ((GridPanel) component).repaintGrid(model);
      }
    }
  }

  @Override
  public void refreshHands() {
    initializeHands();
  }

  @Override
  public CardInterface getSelectedCard() {
    return selectedCard != null ? selectedCard.getCard() : null;
  }

  @Override
  public void clearSelectedCard() {
    deselectCard();
  }

  @Override
  public void setSelectedPanel(GridPanel panel) {
    this.selectedPanel = panel;
  }

  @Override
  public GridPanel getSelectedPanel() {
    return selectedPanel;
  }

  @Override
  public void clearSelectedPanel() {
    this.selectedPanel = null;
  }

  @Override
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }

  @Override
  public void popup(String message) {
    JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public boolean isHandsInitialized() {
    return handsInitialized;
  }
}
