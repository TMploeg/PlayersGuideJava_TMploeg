package helpers.console;

public enum Command{
	MOVE_NORTH("move north", "move to the room to the north"),
	MOVE_EAST("move east", "move to the room to the east"),
	MOVE_SOUTH("move south", "move to the room to south"),
	MOVE_WEST("move west", "move to the room to the west"),
	ENABLE_FOUNTAIN("enable fountain", "enable the Fountain of Objects"),
	HELP("help", "show all commands");
	
	private String commandText;
	private String description;
	
	private Command(String commandText, String description){
		this.commandText = commandText;
		this.description = description;
	}
	
	public String getCommandText(){
		return commandText;
	}
	
	public String getDescription(){
		return description;
	}
	
	public static boolean exists(String commandText){
		if(commandText == null){
			throw new NullPointerException();
		}
		
		if(commandText.length() == 0){
			throw new IllegalArgumentException("command text must have at least 1 character");
		}
		
		for(Command command : Command.values()){
			if(command.commandText.equals(commandText)){
				return true;
			}
		}
		
		return false;
	}
	
	public static Command getFromCommandText(String commandText){
		if(commandText == null){
			throw new NullPointerException();
		}
		
		if(commandText.length() == 0){
			throw new IllegalArgumentException("command text must have at least 1 character");
		}
		
		for(Command command : Command.values()){
			if(command.commandText.equals(commandText)){
				return command;
			}
		}
		
		throw new EnumConstantNotPresentException(Command.class, commandText);
	}
}