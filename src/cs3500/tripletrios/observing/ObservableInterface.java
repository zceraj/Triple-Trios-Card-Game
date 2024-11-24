package cs3500.tripletrios.observing;

/**
 * Interface for an observable object that can be watched by observers.
 */
public interface ObservableInterface {

  /**
   * Adds an observer to this observable object.
   *
   * @param observer the observer to add
   */
  void addObserver(cs3500.tripletrios.observing.Observer observer);

  /**
   * Removes an observer from this observable object.
   *
   * @param observer the observer to remove
   */
  void removeObserver(cs3500.tripletrios.observing.Observer observer);

  /**
   * Notifies all observers of a change in this object.
   */
  void notifyObservers();
}
