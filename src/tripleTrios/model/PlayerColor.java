package tripleTrios.model;

public enum PlayerColor {
  RED,
  BLUE;

  public void setColor(String color){
    try {
      PlayerColor.valueOf(color.toUpperCase());
    } catch (IllegalArgumentException e){
      throw new IllegalArgumentException("Unknown color: " + color);
    }
  }

  @Override
  public String toString() {
    return name().charAt(0) + name().substring(1).toLowerCase();
  }
}