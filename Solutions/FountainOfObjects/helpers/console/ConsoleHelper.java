package helpers.console;

import java.util.Scanner;

public class ConsoleHelper {
  private static final String ANSI_RESET = "\033[0m";

  public static void printColor(String text, ConsoleColor color) {
    System.out.print(getAnsiCode(color) + text + ANSI_RESET);
  }

  public static void printlnColor(String text, ConsoleColor color) {
    System.out.println(getAnsiCode(color) + text + ANSI_RESET);
  }

  private static String getAnsiCode(ConsoleColor color) {
    return "\033[38;2;" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue() + "m";
  }

  public static Command getCommandInput(String message) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print(message + " " + getAnsiCode(ConsoleColor.CYAN));
      String input = scanner.nextLine();

      System.out.print(ANSI_RESET);

      if (Command.exists(input)) {
        return Command.getFromCommandText(input);
      }

      System.out.println(
          "command '"
              + input
              + "' does not exist (enter '"
              + Command.HELP.getCommandText()
              + "' for help");
    }
  }
}
