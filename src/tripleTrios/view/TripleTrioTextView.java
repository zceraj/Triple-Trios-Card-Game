package tripleTrios.view;

import java.io.IOException;
import java.util.Objects;

import tripleTrios.model.GameModel;

public class TripleTrioTextView implements GameView {
  private GameModel model;
  private Appendable appendable;


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
    this.appendable = Objects.requireNonNullElseGet(appendable, StringBuilder::new);

  }



  @Override
  public void render() throws IOException {

  }
}
