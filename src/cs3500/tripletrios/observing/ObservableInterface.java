package cs3500.tripletrios.observing;

import java.util.Observer;

public interface ObservableInterface {

  void addObserver(cs3500.tripletrios.observing.Observer observer);

  void removeObserver(cs3500.tripletrios.observing.Observer observer);

  void notifyObservers();
}
