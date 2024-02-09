import commands.*;
import robot.Robot;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TheOldRobot{
	private static Map<String, RobotCommandFactory> commandFactoryMap = new HashMap<>(){{
		put("north", () -> new NorthCommand());
		put("east", () -> new EastCommand());
		put("south", () -> new SouthCommand());
		put("west", () -> new WestCommand());
		put("on", () -> new OnCommand());
		put("off", () -> new OffCommand());
	}};
	
	public static void main(String[] args){
		Robot robot = new Robot(getCommandInputs());
		robot.executeCommands();
	}
	
	private static List<RobotCommand> getCommandInputs(){
		Scanner scanner = new Scanner(System.in);
		
		List<RobotCommand> commands = new ArrayList<>();
		
		while(commands.size() < Robot.COMMAND_COUNT){
			displayMenu();
			
			System.out.print("command: ");
			String input = scanner.nextLine();
			
			if(input.indexOf(' ') >= 0){
				System.out.println("Please enter one command at a time");
				continue;
			}
			
			if(input.length() < 1){
				System.out.println("Command must have at least one character");
				continue;
			}
	
			boolean foundCommand = false;
			
			for(String key : commandFactoryMap.keySet()){
				if(key.equals(input)){
					commands.add(commandFactoryMap.get(input).create());
					foundCommand = true;
					break;
				}
			}
			
			if(!foundCommand){
				System.out.println("'" + input + "' is not a valid command");
			}
		}
		
		return commands;
	}
	
	private static void displayMenu(){
		System.out.println("Select any combination of " + Robot.COMMAND_COUNT + " of the following");
		
		for(String key : commandFactoryMap.keySet()){
			System.out.println("- " + key);
		}
	}
}