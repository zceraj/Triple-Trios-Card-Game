package tripleTrios.view;


import java.awt.*;

import javax.swing.*;

import tripleTrios.model.PlayerColor;

public class CanvasView extends JPanel {
  private final int rows;
  private final int cols;
  private final int cellSize;
  private final PlayerColor[][] grid;

  public CanvasView(int rows, int cols, int cellSize) {
    this.rows = rows;
    this.cols = cols;
    this.cellSize = cellSize;
    this.grid = new PlayerColor[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        grid[r][c] = null; // Or set a default color
      }
    }
    setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
  }

  // Method to set a player's color in a cell
  public void setCell(int row, int col, PlayerColor color) {
    if (row >= 0 && row < rows && col >= 0 && col < cols) {
      grid[row][col] = color;
      repaint();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Draw grid
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int x = col * cellSize;
        int y = row * cellSize;
        // Draw cell background

        // Draw cell border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, cellSize, cellSize);
      }
    }
  }
}