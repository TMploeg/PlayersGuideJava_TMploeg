	package entities;

import map.Room;

public abstract class Entity {
  private Room location;
  
  public Entity(Room location) {
    this.location = location;
	location.addEntity(this);
  }

  public Room getLocation() {
    return location;
  }
  
  protected void setLocation(Room location){
	this.location = location;
  }
}