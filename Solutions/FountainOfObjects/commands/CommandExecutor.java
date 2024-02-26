package commands;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
  private Map<Command, Runnable> commandActionMap;

  public CommandExecutor() {
    commandActionMap = new HashMap<>();
  }

  public void mapCommandAction(Command command, Runnable runnable) {
    if (runnable == null) {
      throw new NullPointerException("command action is null");
    }

    commandActionMap.put(command, runnable);
  }

  public void execute(Command command) {
    for (Command commandKey : commandActionMap.keySet()) {
      if (command == commandKey) {
        commandActionMap.get(commandKey).run();
        return;
      }
    }

    throw new IllegalArgumentException("command '" + command.toString() + "' is not mapped");
  }
}
