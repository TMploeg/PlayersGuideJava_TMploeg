package map;

public enum MapSize {
  SMALL(new MapProperties(4, 1, 1, 1)),
  MEDIUM(new MapProperties(6, 2, 1, 2)),
  LARGE(new MapProperties(8, 4, 2, 3));

  protected record MapProperties(int size, int pits, int maelstroms, int amaroks) {}

  private MapProperties properties;

  private MapSize(MapProperties properties) {
    this.properties = properties;
  }

  public MapProperties getProperties() {
    return properties;
  }
}
