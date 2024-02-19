package commands;

import java.util.Map;
import java.util.HashMap;

public class CommandExecutor {
	private Map<Command, CommandAction> commandActionMap;
	
	public CommandExecutor() {
		commandActionMap = new HashMap<>();
	}
	
	public void mapCommandAction(Command command, CommandAction commandAction){
		if(commandAction == null){
			throw new NullPointerException("command action is null");
		}
		
		commandActionMap.put(command, commandAction);
	}
	
	public void execute(Command command){
		for(Command commandKey : commandActionMap.keySet()){
			if(command == commandKey){
				commandActionMap.get(commandKey).run();
				return;
			}
		}
		
		throw new IllegalArgumentException("command '" + command.toString() + "' is not mapped");
	}
}