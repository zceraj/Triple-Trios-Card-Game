package tripleTrios.model;

public class Card {
  private String card_name;

  private int north;
  private int south;
  private int east;
  private int west;

  // TO-DO connect the values to the directions

  // constructor for putting the directions with attack integers in the card
  //use map to store the directions and values

  //
  public int getAttackValue (){
    return 0;  // change this
  }

  /**
   * A method that returns all the card contents in a string.
   * @return String of the card in the format "CARD_NAME NORTH SOUTH EAST WEST".
   */
  public String toString(){
    return String.format("%s: N=%d S=%d E=%d W=%d", card_name, north, south, east, west);
  }

}
