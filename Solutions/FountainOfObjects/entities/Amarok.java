package entities;

import map.Room;
import helpers.console.ConsoleColor;

public class Amarok extends Entity {
  public static final ConsoleColor ENTITY_COLOR = ConsoleColor.KUMERA;
	
  public Amarok() {
    super(ENTITY_COLOR);
  }
  
  @Override
  protected String getDeathMessage(){
	return "You hear a distored yelp from an adjacent room";  
  }
  
  @Override
  protected String getAmbianceMessage(){
	return "You smell a rotten stench";  
  }
  
  @Override
  protected String getInteractMessage(){
	return "You where mauled by an " + this.getClass().getSimpleName().toLowerCase();  
  }
}