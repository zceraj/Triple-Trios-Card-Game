package tripleTrios.view;

import java.io.IOException;
import java.util.Objects;

import tripleTrios.model.Card;
import tripleTrios.model.Cell;
import tripleTrios.model.GameModel;

public class TripleTrioTextView implements GameView {
  private final GameModel model;
  private final Appendable stringToRender;


  /**
   * constructs a text view using a mdel and an appendable.
   *
   * @param model
   * @param appendable
   */
  public TripleTrioTextView(GameModel model, Appendable appendable) {
    if (model == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    this.stringToRender = Objects.requireNonNullElseGet(appendable, StringBuilder::new);

  }

  @Override
  public void render() throws IOException {
    stringToRender
            .append("Grid:/n")
            .append(model.getGameGrid().toString());

    // Cards (C): Show loaded card details
    stringToRender.append("\nCards:\n");
    for (Card card : model.getCurPlayer().getHand()) {
      stringToRender.append(card.toString()).append("\n");
    }
  }
}
