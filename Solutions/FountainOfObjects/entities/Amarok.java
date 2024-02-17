package entities;

import map.Room;

public class Amarok extends Entity {
  private Amarok(Room location) {
    super(location);
  }

  public static void createInRoom(Room room) {
    new Amarok(room);
  }
}