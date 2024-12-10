package cs3500.tripletrios.observing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract class that can be observed.
 * Manages a list of observers and notifies them of changes.
 */
public abstract class Observable implements ObservableInterface {

  private final List<Observer> observers = new ArrayList<>();

  /**
   * Adds an observer to be notified of updates.
   *
   * @param observer the observer to add
   */
  @Override
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  /**
   * Removes an observer from the notification list.
   *
   * @param observer the observer to remove
   */
  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  /**
   * Notifies all registered observers of a change.
   */
  @Override
  public void notifyObservers() throws IOException {
    for (Observer observer : observers) {
      observer.update();
    }
  }
}
