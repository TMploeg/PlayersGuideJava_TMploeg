package entities;

import map.Room;
import helpers.console.ConsoleColor;

public class Amarok extends Entity {
  private static final ConsoleColor ENTITY_COLOR = ConsoleColor.KUMERA;
	
  private Amarok(Room location) {
    super(location, ENTITY_COLOR);
  }

  public static void createInRoom(Room room) {
    new Amarok(room);
  }
  
  public String getDeathMessage(){
	return "You hear a distored yelp from an adjacent room";  
  }
}