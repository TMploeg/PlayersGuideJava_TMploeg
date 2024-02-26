package commands;

import java.util.Optional;

public enum Command {
  MOVE_NORTH("move to the room to the north"),
  MOVE_EAST("move to the room to the east"),
  MOVE_SOUTH("move to the room to south"),
  MOVE_WEST("move to the room to the west"),
  SHOOT_NORTH("shoot an arrow to the room to the north"),
  SHOOT_EAST("shoot an arrow to the room to the east"),
  SHOOT_SOUTH("shoot an arrow to the room to the south"),
  SHOOT_WEST("shoot an arrow to the room to the west"),
  ENABLE_FOUNTAIN("enable the Fountain of Objects"),
  HELP("show all commands");

  private String description;

  private Command(String description) {
    this.description = description;
  }

  public String getCommandText() {
    String result = "";

    for (String splitPart : this.toString().split("_")) {
      if (result.length() > 0) {
        result += " ";
      }

      result += splitPart.toLowerCase();
    }

    return result;
  }

  public String getDescription() {
    return description;
  }

  public static Optional<Command> getFromName(String name) {
    if (name == null) {
      throw new NullPointerException();
    }

    for (Command command : Command.values()) {
      if (command.getCommandText().equals(name)) {
        return Optional.of(command);
      }
    }

    return Optional.empty();
  }
}
