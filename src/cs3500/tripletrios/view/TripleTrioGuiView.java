package cs3500.tripletrios.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BorderFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
import java.util.List;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.CellInterface;
import cs3500.tripletrios.model.IPlayer;
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.model.ReadOnlyGameModel;
import cs3500.tripletrios.observing.Observer;

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
  private final List<Observer> observers = new ArrayList<>();
  private final IPlayer player;
  private GridPanel selectedPanel;
  private final IPlayer startingPlayer;
  private final IPlayer notStartingPlayer;
  private final boolean handsInitialized;


  /**
   * Initializes the frame
   * and layout, creates panels for the game grid and player hands, and calls initialization
   * methods to populate the view based on the current game state.
   *
   * @param model the read-only model representing the current game state.
   */
  public TripleTrioGuiView(ReadOnlyGameModel model, IPlayer player) {
    this.handsInitialized = false;
    this.model = model;
    this.player = player;
    this.startingPlayer = model.getCurPlayer();
    this.notStartingPlayer = model.getOtherPlayer();
    this.gridPanel = new JPanel(
            new GridLayout(model.getGameGrid().getRows(), model.getGameGrid().getCols()));
    this.leftColumnPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), 1));
    this.rightColumnPanel = new JPanel(new GridLayout(model.getGameGrid().getRows(), 1));
    this.selectedCard = null;

    setTitle("Three Trios Game         " + "This player: " + player.getName());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setSize(800, 600);
    this.setResizable(true);

    add(gridPanel, BorderLayout.CENTER);
    add(leftColumnPanel, BorderLayout.WEST);
    add(rightColumnPanel, BorderLayout.EAST);

    initializeGrid();
    initializeQuitButton();

    repaint();
  }

  /**
   * Initializes the game grid based on the model's current game state, creating a grid of
   * GricCell instances representing each cell in the game. This method updates the grid panel to
   * display each cell as specified by the model.
   */
  private void initializeGrid() {
    gridPanel.removeAll();
    List<List<CellInterface>> cells = new ArrayList<>();
    // Retrieve the cells and add them to the panel
    for (int row = 0; row < model.getGameGrid().getRows(); row++) {
      List<CellInterface> rowCells = new ArrayList<>();
      for (int col = 0; col < model.getGameGrid().getCols(); col++) {
        CellInterface cell = model.getGameGrid().getCell(row, col);
        GridPanel gridCellPanel = new GridPanel(cell, row, col, model, player, this);

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
  @Override
  public void initializeHands() {
    for (int times = 0; times < 2; times++) {
      if (!handsInitialized) {
        leftColumnPanel.removeAll();
        rightColumnPanel.removeAll();
        List<CardInterface> leftColumnCards = startingPlayer.getCurrentHand();
        List<CardInterface> rightColumnCards = notStartingPlayer.getCurrentHand();
        Color leftColumnCardsColor;
        Color rightColumnCardsColor;

        Color red = new Color(200, 50, 100);
        Color blue = new Color(50, 100, 200);

        if (startingPlayer.getColor().equals(PlayerColor.BLUE)) {
          leftColumnCardsColor = blue;
          rightColumnCardsColor = red;
        } else {
          leftColumnCardsColor = red;
          rightColumnCardsColor = blue;
        }

        for (int i = 0; i < leftColumnCards.size(); i++) {
          CardPanel cardPanel = new CardPanel(
                  leftColumnCards.get(i), leftColumnCardsColor, this, this);
          cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              try {
                handleCardClick(cardPanel);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            }
          });
          leftColumnPanel.add(cardPanel);
        }
        for (int i = 0; i < rightColumnCards.size(); i++) {
          CardPanel cardPanel = new CardPanel(
                  rightColumnCards.get(i), rightColumnCardsColor, this, this);
          cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              try {
                handleCardClick(cardPanel);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            }
          });
          rightColumnPanel.add(cardPanel);
        }
      }
    }
    if (selectedCard != null) {
      refreshHands();
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
  private void handleCardClick(CardPanel cardPanel) throws IOException {
    if (model.getCurPlayer() == player && model.getPlayerFromCard(cardPanel.getCard()) == player) {
      if (this.selectedCard == cardPanel) {
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Reset border
        selectedCard = null;
        System.out.println("Deselected card.");
      } else {
        if (selectedCard != null) {
          selectedCard.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Reset old card
        }

        selectedCard = cardPanel;
        IPlayer playerOwner = model.getPlayerFromCard(cardPanel.getCard());

        System.out.println("Selected card: "
                + selectedCard.getCard().getCardName()
                + " owned by "
                + playerOwner.getName());
        selectedCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
      }
      notifyObservers();
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
    if (player == model.getCurPlayer()) {
      if (player == startingPlayer) {
        leftColumnPanel.remove(selectedCard);
        leftColumnPanel.repaint();
      } else {
        rightColumnPanel.remove(selectedCard);
        leftColumnPanel.repaint();
      }
    }
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
        ((GridPanel) component).repaintGrid(model);
      }
    }
  }

  /**
   * Gets the card that is selected.
   *
   * @return the selected card
   */
  @Override
  public CardInterface getSelectedCard() {
    if (selectedCard == null) {
      return null;
    }
    return selectedCard.getCard();
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
  public void notifyObservers() throws IOException {
    for (Observer observer : observers) {
      observer.update();
    }
  }

  @Override
  public void popup(String message) {
    javax.swing.SwingUtilities.invokeLater(() -> {
      javax.swing.JOptionPane.showMessageDialog(
              null,
              message,
              "Here ye! Here ye!",
              javax.swing.JOptionPane.INFORMATION_MESSAGE
      );
    });
  }

  /**
   * resets the selected card.
   */
  @Override
  public void clearSelectedCard() throws IOException {
    selectedCard = null;
    notifyObservers();
  }

  // Method to initialize the Quit button
  private void initializeQuitButton() {
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);  // This will terminate the program when the button is clicked
      }
    });

    // Add the button to the bottom of the window (BorderLayout.SOUTH)
    add(quitButton, BorderLayout.SOUTH);
  }

  @Override
  public void setSelectedPanel(GridPanel panel) {
    this.selectedPanel = panel;
  }

  @Override
  public GridPanel getSelectedPanel() {
    return this.selectedPanel;
  }

  @Override
  public void clearSelectedPanel() {
    this.selectedPanel.repaintGrid(model);
    setSelectedPanel(null);
  }

  @Override
  public boolean isHandsInitialized() {
    return this.handsInitialized;
  }
}
