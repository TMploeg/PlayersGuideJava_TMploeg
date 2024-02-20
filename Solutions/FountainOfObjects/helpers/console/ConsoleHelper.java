package helpers.console;

import helpers.console.menu.*;
import commands.Command;
import java.util.Scanner;
import java.util.Optional;

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

      if (input == null || input.length() == 0) {
        System.out.println("input must have at least one character");
        continue;
      }

	  Optional<Command> command = Command.getFromName(input);
	  
      if (command.isPresent()) {
        return command.get();
      }

      System.out.println(
          "command '"
              + input
              + "' does not exist (enter '"
              + Command.HELP.getCommandText()
              + "' for help");
    }
  }

  public static <TValue> TValue getMenuInput(Menu<TValue> menu) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      displayMenu(menu);
      System.out.print("Please enter the number of the menu item you want: ");

      String menuItemInput = scanner.nextLine();

      Integer menuNumber = tryParsePositiveNumber(menuItemInput);

      if (menuNumber == null) {
        System.out.println("'" + menuItemInput + "' is not a valid integer");
        continue;
      }

      if (menuNumber < 1 || menuNumber > menu.getMenuItems().size()) {
        System.out.println("menu item '" + menuNumber + "' does not exist");
        continue;
      }

      return menu.getMenuItem(menuNumber - 1).getValue();
    }
  }

  private static <TValue> void displayMenu(Menu<TValue> menu) {
    int itemNr = 1;

    for (MenuItem<TValue> item : menu.getMenuItems()) {
      System.out.println(itemNr + ". " + item);

      itemNr++;
    }
  }

  private static Integer tryParsePositiveNumber(String str) {
    if (str == null || str.length() == 0) {
      return null;
    }

    String maxValueString = Integer.toString(Integer.MAX_VALUE);

    if (str.length() > maxValueString.length()) {
      return null;
    }

    boolean possiblyOutOfRange = str.length() == maxValueString.length();

    for (int i = 0; i < str.length(); i++) {
      char current = str.charAt(i);

      if (!Character.isDigit(current)) {
        return null;
      }

      if (possiblyOutOfRange) {
        int compareValue = Character.compare(current, maxValueString.charAt(i));
        if (compareValue > 0) {
          return null;
        }

        if (compareValue < 0) {
          possiblyOutOfRange = false;
        }
      }
    }

    return Integer.parseInt(str);
  }
}
