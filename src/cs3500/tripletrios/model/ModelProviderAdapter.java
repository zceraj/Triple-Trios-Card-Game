package cs3500.tripletrios.model;

import cs3500.tripletrios.provider.model.ReadOnlyThreeTriosModelInterface;
import cs3500.tripletrios.provider.model.PlayerColor;
import cs3500.tripletrios.provider.model.GameState;
import cs3500.tripletrios.provider.model.ThreeTriosModelInterface;
import cs3500.tripletrios.provider.model.card.AttackValue;
import cs3500.tripletrios.provider.model.card.CardColor;
import cs3500.tripletrios.provider.model.card.CustomCard;
import cs3500.tripletrios.provider.model.cell.Cell;
import cs3500.tripletrios.provider.model.cell.CellState;
import cs3500.tripletrios.provider.model.grid.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapts your GameModel to the provider's ReadOnlyThreeTriosModelInterface.
 */
public class ModelProviderAdapter implements ReadOnlyThreeTriosModelInterface, ThreeTriosModelInterface {
  private final GameModel model;

  /**
   * Constructs the adapter.
   *
   * @param model Your game model.
   */
  public ModelProviderAdapter(GameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  @Override
  public CellState getCellStateAt(int row, int col) {
    CellInterface cell = model.getGameGrid().getCell(row, col);

    if (cell.isEmpty()) {
      return CellState.EMPTY;
    } else if (!cell.isCardCell()) {
      return CellState.HOLE;
    } else {
      // Determine the cell's color based on the player owning it
      IPlayer owner = model.getCellsPlayer(row, col);
      if (owner == null) {
        return CellState.EMPTY;
      }
      return owner.getColor().equalsIgnoreCase("RED") ? CellState.RED : CellState.BLUE;
    }
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    IPlayer currentPlayer = model.getCurPlayer();
    return currentPlayer.getColor().equalsIgnoreCase("RED") ? PlayerColor.RED : PlayerColor.BLUE;
  }

  @Override
  public List<CustomCard> getCurrentPlayerHand() {
    return convertHand(model.getCurPlayer().getCurrentHand());
  }

  @Override
  public List<CustomCard> getPlayerHand(PlayerColor player) {
    IPlayer targetPlayer = player == PlayerColor.RED ? model.getCurPlayer() : model.getOtherPlayer();
    return convertHand(targetPlayer.getCurrentHand());
  }

  @Override
  public Grid getGrid() {
    return new Grid() {
      @Override
      public Cell getCell(int row, int col) {
        CellInterface yourCell = model.getGameGrid().getCell(row, col);
        return adaptCell(yourCell);
      }

      @Override
      public void placeCard(CustomCard card, int row, int col) {
        throw new UnsupportedOperationException("This is a read-only adapter.");
      }

      @Override
      public int getRows() {
        return model.getGameGrid().getRows();
      }

      @Override
      public int getCols() {
        return model.getGameGrid().getCols();
      }

      @Override
      public List<Cell> getCardCells() {
        List<Cell> cells = new ArrayList<>();
        for (int row = 0; row < getRows(); row++) {
          for (int col = 0; col < getCols(); col++) {
            CellInterface cell = model.getGameGrid().getCell(row, col);
            if (cell.isCardCell() && !cell.isEmpty()) {
              cells.add(adaptCell(cell));
            }
          }
        }
        return cells;
      }

      @Override
      public int getEmptyCellCount() {
        int count = 0;
        for (int row = 0; row < getRows(); row++) {
          for (int col = 0; col < getCols(); col++) {
            CellInterface cell = model.getGameGrid().getCell(row, col);
            if (cell.isCardCell() && cell.isEmpty()) {
              count++;
            }
          }
        }
        return count;
      }

      @Override
      public Cell[] getAdjacentCells(int row, int col) {
        Cell[] adjacentCells = new Cell[4];
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length; i++) {
          CellInterface yourCell = model.getGameGrid().getAdjacentCells(row, col, directions[i]);
          adjacentCells[i] = yourCell != null ? adaptCell(yourCell) : null;
        }
        return adjacentCells;
      }

      @Override
      public Grid copy() {
        throw new UnsupportedOperationException("Copy operation is not supported for this adapter.");
      }
    };
  }

  @Override
  public int getScore(PlayerColor player) {
    IPlayer targetPlayer = player == PlayerColor.RED ? model.getCurPlayer() : model.getOtherPlayer();
    return model.getScore(targetPlayer);
  }

  @Override
  public GameState getGameState() {
    if (!model.isGameStarted()) {
      return GameState.NOT_STARTED;
    }
    if (model.isGameOver()) {
      IPlayer winner = model.getWinner();
      if (winner == null) {
        return GameState.EARLY_QUIT;
      }
      return winner.getColor().equalsIgnoreCase("RED") ? GameState.RED_WIN : GameState.BLUE_WIN;
    }
    return GameState.IN_PROGRESS;
  }

  // Helper Methods

  /**
   * Converts a hand of your CardInterface into a list of CustomCard objects.
   */
  private List<CustomCard> convertHand(List<CardInterface> hand) {
    List<CustomCard> converted = new ArrayList<>();
    for (CardInterface card : hand) {
      converted.add(adaptCard(card));
    }
    return converted;
  }

  /**
   * Adapts our CardInterface to the provider's CustomCard interface.
   */
  private CustomCard adaptCard(CardInterface card) {
    return new CustomCard() {
      @Override
      public String getName() {
        return card.getCardName();
      }

      @Override
      public String toString() {
        return card.toString();
      }

      @Override
      public AttackValue getAttackValue(cs3500.tripletrios.provider.model.card.Direction direction) {
        // Convert the provider's direction enum to your direction enum
        Direction ourDirection = Direction.valueOf(direction.name());

        // Get the attack value from your card as an integer
        int attackValue = card.getAttackValueAsInt(ourDirection);

        // Map the integer attack value to the provider's AttackValue enum
        for (AttackValue value : AttackValue.values()) {
          if (value.getStrength() == attackValue) {
            return value;
          }
        }

        // If no matching AttackValue is found, throw an exception (this shouldn't happen)
        throw new IllegalArgumentException("Invalid attack value: " + attackValue);
      }

      @Override
      public CardColor getCurrentColor() {
        return null; // Provide mapping if your cards have color logic
      }

      @Override
      public void setNewColor(CardColor newColor) {
        throw new UnsupportedOperationException("This operation is not supported.");
      }

      @Override
      public CustomCard copy() {
        return this; // cards might not need deep copies here
      }
    };
  }

  /**
   * Adapts our CellInterface to the provider's Cell interface.
   */
  private Cell adaptCell(CellInterface yourCell) {
    return new Cell() {
      @Override
      public boolean isHole() {
        return !yourCell.isCardCell();
      }

      @Override
      public boolean isEmpty() {
        return yourCell.isEmpty();
      }

      @Override
      public PlayerColor getCellColor() {
        // If the cell is not a card cell, it has no color
        if (!yourCell.isCardCell()) {
          return null;
        }

        // If the cell is empty, it has no color
        if (yourCell.isEmpty()) {
          return null;
        }

        // Determine the owner of the card in the cell and map to PlayerColor
        IPlayer owner = model.getCellsPlayer(yourCell.getRow(), yourCell.getCol());
        if (owner.getColor().equals("RED")) { // Assuming player colors are Strings
          return PlayerColor.RED;
        } else if (owner.getColor().equals("BLUE")) {
          return PlayerColor.BLUE;
        }

        // Default case: return null for unrecognized or invalid states
        return null;
      }


      @Override
      public CellState getCellState() {
        // Check if the cell is not a card cell (e.g., a hole)
        if (!yourCell.isCardCell()) {
          return CellState.HOLE;
        }
        // Check if the cell is empty
        if (yourCell.isEmpty()) {
          return CellState.EMPTY;
        }
        // If the cell is neither empty nor a hole, check the owner and map to RED or BLUE
        IPlayer owner = model.getCellsPlayer(yourCell.getRow(), yourCell.getCol());
        if (owner.getColor().equals("RED")) { // Assuming your player color logic uses Strings
          return CellState.RED;
        } else if (owner.getColor().equals("BLUE")) {
          return CellState.BLUE;
        }
        // Default case: throw exception for invalid state (optional)
        throw new IllegalArgumentException("Invalid cell state for cell at (" + yourCell.getRow() + ", " + yourCell.getCol() + ")");
      }


      @Override
      public CustomCard getCard() {
        return yourCell.isEmpty() ? null : adaptCard(yourCell.getCard());
      }

      @Override
      public void playCard(CustomCard card) {
        throw new UnsupportedOperationException("This is a read-only adapter.");
      }

      @Override
      public void flipCard(CardColor opponentColor) {
        throw new UnsupportedOperationException("This is a read-only adapter.");
      }

      @Override
      public Cell copy() {
        return this;
      }
    };
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck) throws IOException {
    if (gameGrid == null || deck == null) {
      throw new IllegalArgumentException("Game grid and deck cannot be null.");
    }
    List<CardInterface> adaptedDeck = new ArrayList<>();
    for (CustomCard card : deck) {
      adaptedDeck.add(adaptCustomCardToCardInterface(card));
    }
    model.startGame(adaptedDeck);
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck, boolean shuffle) throws IOException {
    if (gameGrid == null || deck == null) {
      throw new IllegalArgumentException("Game grid and deck cannot be null.");
    }
    List<CardInterface> adaptedDeck = new ArrayList<>();
    for (CustomCard card : deck) {
      adaptedDeck.add(adaptCustomCardToCardInterface(card));
    }
    model.startGame(adaptedDeck);
  }

  @Override
  public void playTurn(int row, int col, int handIndex) {
    //not needed
  }

  @Override
  public Grid endGame() {
    if (model.isGameOver()) {
      return getGrid();
    }
    throw new IllegalStateException("Game is not over yet.");
  }

  @Override
  public ThreeTriosModelInterface copy() {
    return new ModelProviderAdapter(model);
  }

  private CardInterface adaptCustomCardToCardInterface(CustomCard card) {
    return new CardInterface() {
      private int row = -1; // Default uninitialized value for row
      private int col = -1; // Default uninitialized value for column

      @Override
      public void addRow(int row) {
        this.row = row;
      }

      @Override
      public void addCol(int col) {
        this.col = col;
      }

      @Override
      public String getAttackValue(Direction direction) {
        cs3500.tripletrios.provider.model.card.Direction providerDirection =
                cs3500.tripletrios.provider.model.card.Direction.valueOf(direction.name());

        return card.getAttackValue(providerDirection).toString();
      }

      @Override
      public int getRow() {
        return row;
      }

      @Override
      public int getCol() {
        return col;
      }

      @Override
      public String getCardName() {
        return card.getName();
      }

      @Override
      public int getAttackValueAsInt(Direction direction) {
        cs3500.tripletrios.provider.model.card.Direction providerDirection =
                cs3500.tripletrios.provider.model.card.Direction.valueOf(direction.name());

        return card.getAttackValue(providerDirection).getStrength();
      }
    };
  }

}
