package cs3500.tripletrios.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.tripletrios.model.CardInterface;

/**
 * Sets up the cards and grid for the game.
 */
public class SetUp {

  private final String cardFilePath;
  private final String gridFilePath;

  /**
   * Creates a setup with file paths for cards and grid.
   * If no paths are given, default files are used.
   *
   * @param cardFilePath the path to the card file, or null for default
   * @param gridFilePath the path to the grid file, or null for default
   */
  public SetUp(String cardFilePath, String gridFilePath) {
    this.cardFilePath = (cardFilePath == null)
            ? "." + File.separator + "TESTINGFILES" + File.separator + "test_cards.txt"
            : cardFilePath;

    this.gridFilePath = (gridFilePath == null)
            ? "." + File.separator + "TESTINGFILES" + File.separator + "valid_grid.text"
            : gridFilePath;
  }

  /**
   * Reads the card file and gets the list of cards.
   *
   * @return a list of cards
   * @throws RuntimeException if the file cannot be read
   */
  public List<CardInterface> setCards() {
    try {
      return new CardFileReader(cardFilePath).getCardDatabase();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Reads the grid file and gets the grid layout.
   *
   * @return a 2D boolean array representing the grid
   * @throws RuntimeException if the file cannot be read
   */
  public boolean[][] setGrid() {
    try {
      return new GridFileReader(gridFilePath).getGrid();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
