package map;

public enum Cardinal {
  NORTH,
  EAST,
  SOUTH,
  WEST;
  
  private Cardinal(){
  
  }

  public Cardinal opposite() {
    return switch (this) {
      case NORTH -> SOUTH;
      case EAST -> WEST;
      case SOUTH -> NORTH;
      case WEST -> EAST;
      default -> throw new RuntimeException();
    };
  }
}
