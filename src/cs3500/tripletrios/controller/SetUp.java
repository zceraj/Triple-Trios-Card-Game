package cs3500.tripletrios.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.tripletrios.model.CardInterface;

public class SetUp {
  private final String cardFilePath;
  private final String gridFilePath;

  public SetUp(String cardFilePath, String gridFilePath){
    if (cardFilePath == null) {
      this.cardFilePath = "."
              + File.separator
              + "TESTINGFILES"
              + File.separator
              + "test_cards.txt";
    }
    else { this.cardFilePath = cardFilePath; }
    if (gridFilePath == null) {
      this.gridFilePath = "."
              + File.separator
              + "TESTINGFILES"
              + File.separator
              + "valid_grid.text";
    }
    else { this.gridFilePath = gridFilePath; }
  }

  public List<CardInterface> setCards(){
    try {
      return new CardFileReader(cardFilePath).getCardDatabase();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean[][] setGrid(){
    try {
      return new GridFileReader(gridFilePath).getGrid();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
