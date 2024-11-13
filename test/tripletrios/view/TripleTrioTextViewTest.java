package tripletrios.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cs3500.tripletrios.model.Card;
import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.GameModel;
import cs3500.tripletrios.model.GameModelImpl;
import cs3500.tripletrios.model.Grid;
import cs3500.tripletrios.model.HumanPlayer;
import cs3500.tripletrios.model.PlayerColor;
import cs3500.tripletrios.view.TripleTrioTextView;

import java.io.File;
import java.io.StringWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * tests for the TripleTrioTextView class.
 */
public class TripleTrioTextViewTest {
  private GameModel game;
  private StringWriter appendable;
  private TripleTrioTextView view;

  /**
   * Sets up everyting before each test.
   */
  @BeforeEach
  public void setUp() throws Exception {
    HumanPlayer player1 = new HumanPlayer("Player1", PlayerColor.BLUE);
    HumanPlayer player2 = new HumanPlayer("Player2", PlayerColor.RED);
    boolean[][] grid = {
            {true, true, false},
            {false, true, true},
            {true, false, true}
    };

    List<CardInterface> cards = List.of(
            new Card("Tiger", 4, 2, 5, 3),
            new Card("Elephant", 5, 1, 4, 2),
            new Card("Giraffe", 2, 4, 3, 5),
            new Card("Zebra", 1, 3, 5, 4),
            new Card("Panda", 4, 4, 4, 4),
            new Card("Wolf", 3, 2, 1, 5)
    );

    game = new GameModelImpl(
            grid,
            player1,
            player2);

    game.startGame(cards);

    appendable = new StringWriter();
    view = new TripleTrioTextView(game, appendable);
  }

  /**
   * Tests the render method of TripleTrioTextView.
   * It verifies that the output is correct based on the GameModel's state.
   */
  @Test
  public void testRender() throws IOException {
    // Assume the game has been set up with the necessary data
    view.render();

    // Prepare the expected output based on the game state
    String expectedGrid = game.getGameGrid().toString();
    List<CardInterface> hand = game.getCurPlayer().getHand();
    StringBuilder expectedCards = new StringBuilder();

    for (CardInterface card : hand) {
      expectedCards.append(card.toString()).append("\n");
    }

    String expectedOutput = "Grid:/n" + expectedGrid + "\nCards:\n" + expectedCards;

    // Assert that the output is as expected
    assertEquals(expectedOutput, appendable.toString());
  }

  /**
   * Tests the constructor for TripleTrioTextView with a null model.
   * It verifies that an IllegalArgumentException is thrown.
   */
  @Test
  public void testConstructor_NullModel() {
    assertThrows(IllegalArgumentException.class, () -> new TripleTrioTextView(null, appendable));
  }

  /**
   * Tests the constructor for TripleTrioTextView with a null appendable.
   * It verifies that a StringBuilder is used as the default appendable.
   */
  @Test
  public void testConstructor_NullAppendable() {
    TripleTrioTextView viewWithDefaultAppendable = new TripleTrioTextView(game, null);
    assertNotNull(viewWithDefaultAppendable);
  }
}

