package map;

public enum MapSize {
  SMALL(new MapProperties(4, 1, 1, 1, 5)),
  MEDIUM(new MapProperties(6, 2, 1, 2, 8)),
  LARGE(new MapProperties(8, 4, 2, 3, 12));

  protected record MapProperties(int size, int pits, int maelstroms, int amaroks, int arrows) {}

  private MapProperties properties;

  private MapSize(MapProperties properties) {
    this.properties = properties;
  }

  public MapProperties getProperties() {
    return properties;
  }
}
