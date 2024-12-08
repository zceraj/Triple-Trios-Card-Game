package cs3500.tripletrios.observing;

/**
 * Interface for an observer that reacts to changes in an observable object.
 */
public interface Observer {

  /**
   * Called when the observable object notifies its observers of a change.
   */
  void update();
}
