package helpers.console;

public enum ConsoleColor {
  LIGHT_PURPLE(163, 118, 176),
  PINK(207, 39, 131),
  BLUE(56, 132, 232),
  CYAN(52, 227, 227),
  TEAL(93, 245, 144),
  YELLOW(230, 223, 23),
  KUMERA(143, 125, 34),
  GREEN(27, 222, 27),
  WHITE(255, 255, 255),
  LIGHT_GRAY(120, 120, 120),
  DARK_RED(140, 10, 0),
  RED(217, 0, 0);

  private int red;
  private int green;
  private int blue;

  private ConsoleColor(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public int getRed() {
    return red;
  }

  public int getGreen() {
    return green;
  }

  public int getBlue() {
    return blue;
  }
}
