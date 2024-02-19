package entities;

import map.Room;
import helpers.console.ConsoleColor;
import helpers.console.ColoredItem;

public abstract class Entity extends ColoredItem implements Killable {
  private Room location;

  public Entity(Room location, ConsoleColor entityColor) {
	super(entityColor);
	
    this.location = location;
    location.addEntity(this);
  }

  public Room getLocation() {
    return location;
  }

  protected void setLocation(Room location) {
    this.location = location;
  }
}
