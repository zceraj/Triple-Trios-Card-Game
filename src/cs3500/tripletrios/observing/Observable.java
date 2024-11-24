package cs3500.tripletrios.observing;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable implements ObservableInterface {
  private final List<Observer> observers = new ArrayList<>();

  @Override
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers){
      observer.update();
    }
  }
}
