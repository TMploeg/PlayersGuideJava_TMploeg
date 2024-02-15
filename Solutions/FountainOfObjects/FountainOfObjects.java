import game.*;

public class FountainOfObjects {
  public static void main(String[] args) {
    boolean cheatMode = false;

    if (args != null) {
      for (String arg : args) {
        if (arg.equals("-cheat")) {
          cheatMode = true;
          break;
        }
      }
    }

    Game game = new Game(cheatMode);
  }
}
