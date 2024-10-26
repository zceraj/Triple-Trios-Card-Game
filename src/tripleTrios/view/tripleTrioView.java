package tripleTrios.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tripleTrios.model.PlayerColor;

public class tripleTrioView extends JFrame {
  private JLabel playerTurnLabel;
  private JButton playTurnButton;
  private JComboBox<PlayerColor> colorPicker;
  private JLabel selectedColorLabel;

  // Constructor
  public tripleTrioView() {
    setTitle("Turn-Based Game");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    playerTurnLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER);
    playerTurnLabel.setFont(new Font("Arial", Font.BOLD, 18));

    playTurnButton = new JButton("Play Turn");
    playTurnButton.addActionListener(new PlayTurnAction());

    colorPicker = new JComboBox<>(PlayerColor.values());
    colorPicker.addActionListener(new ColorSelectionAction());

    selectedColorLabel = new JLabel("Selected Color: None", SwingConstants.CENTER);

    add(playerTurnLabel, BorderLayout.NORTH);
    add(playTurnButton, BorderLayout.SOUTH);
    add(colorPicker, BorderLayout.CENTER);
    add(selectedColorLabel, BorderLayout.EAST);
    setVisible(true);
  }

  private class PlayTurnAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      playerTurnLabel.setText("Turn played!");
      JOptionPane.showMessageDialog(tripleTrioView.this, "Player has played their turn!", "Turn Complete", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private class ColorSelectionAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      PlayerColor selectedColor = (PlayerColor) colorPicker.getSelectedItem();
      selectedColorLabel.setText("Selected Color: " + selectedColor);
    }
  }
}
