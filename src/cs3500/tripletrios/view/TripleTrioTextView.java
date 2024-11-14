package cs3500.tripletrios.view;

import java.io.IOException;
import java.util.Objects;

import cs3500.tripletrios.model.CardInterface;
import cs3500.tripletrios.model.GameModel;

/**
 * creates a textual view of the state of the model.
 */
public class TripleTrioTextView implements GameView {
  private final GameModel model;
  private final Appendable stringToRender;


  /**
   * constructs a text view using a mdel and an appendable.
   *
   * @param model      model that the player is playing on.
   * @param appendable an appendable that is passed back to the controller.
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
    for (CardInterface card : model.getCurPlayer().getHand()) {
      stringToRender.append(card.toString()).append("\n");
    }
  }
}
