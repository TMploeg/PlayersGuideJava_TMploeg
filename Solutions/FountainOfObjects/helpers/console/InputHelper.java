package helpers.console;

import menu.*;
import helpers.parsers.IntegerParser;
import java.util.Scanner;
import commands.Command;
import java.util.Optional;

public class InputHelper {
  public static Command getCommandInput(String message) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print(message + " ");
	  
	  ConsoleHelper.setConsoleColor(ConsoleColor.CYAN);
      
	  String input = scanner.nextLine();

      ConsoleHelper.resetConsoleColor();

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

	  Optional<Integer> menuNumber = IntegerParser.tryParse(menuItemInput);

      if (!menuNumber.isPresent()) {
        System.out.println("'" + menuItemInput + "' is not a valid integer");
        continue;
      }

      if (menuNumber.get() < 1 || menuNumber.get() > menu.getMenuItems().size()) {
        System.out.println("menu item '" + menuNumber + "' does not exist");
        continue;
      }

      return menu.getMenuItem(menuNumber.get() - 1).getValue();
    }
  }

  private static <TValue> void displayMenu(Menu<TValue> menu) {
    int itemNr = 1;

    for (MenuItem<TValue> item : menu.getMenuItems()) {
      System.out.println(itemNr + ". " + item);

      itemNr++;
    }
  }
}