package cs3500.tripletrios.view;

import java.awt.*;

import javax.swing.*;

import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.Cell;

public class GridPanel extends JPanel{


  private final Cell cell;
  private final int index;

  public GridPanel(Cell cell, int index) {
    this.cell = cell;
    this.index = index;
    setPreferredSize(new Dimension(80, 120));
    if (cell.isCardCell()) {
      setBackground(new Color(200, 150, 255));
    }
    else {
      setBackground(Color.LIGHT_GRAY);
    }
    setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150)));
  }

  public Card getCard() {
    return cell.getCard();
  }

  public int getIndex() {
    return index;
  }
}
