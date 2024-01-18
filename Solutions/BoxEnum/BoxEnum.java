import java.util.Scanner;

public class BoxEnum{
	private static BoxState _state = BoxState.CLOSED;
	
	private static boolean _running = false;
	
	public static void main(String[] args){
		run();
	}
	
	private static void run(){
		lock();
		
		_running = true;
		while(_running){
			BoxCommand command = getCommandInput();
			executeCommand(command);
		}
	}
	
	private static void open(){
		if(_state == BoxState.LOCKED){
			System.out.print("The box is locked, ");
		}
		else if(_state == BoxState.OPEN){
			System.out.print("The box is already open, ");
		}
		else{
			_state = BoxState.OPEN;
			System.out.print("The box is open, ");
		}
	}
	
	private static void close(){
		if(_state != BoxState.OPEN){
			System.out.print("The box is already closed, ");
		}
		else{
			_state = BoxState.CLOSED;
			System.out.print("The box is closed, ");
		}
	}
	
	private static void unlock(){
		if(_state != BoxState.LOCKED){
			System.out.print("The box is already unlocked, ");
		}
		else{
			_state = BoxState.CLOSED;
			System.out.print("The box is unlocked, ");
		}
	}
	
	private static void lock(){
		if(_state == BoxState.LOCKED){
			System.out.print("The box is already locked, ");
		}
		else if(_state == BoxState.OPEN){
			System.out.print("The box is not closed, ");
		}
		else{
			_state = BoxState.LOCKED;
			System.out.print("The box is locked, ");
		}
	}
	
	private static BoxCommand getCommandInput(){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.print("what do you want to do? ");
			String inputCommand = scanner.nextLine();
			
			BoxCommand selectedCommand = null;
			
			for(BoxCommand command : BoxCommand.values()){
				if(command.getName().equals(inputCommand)){
					selectedCommand = command;
					break;
				}
			}
			
			if(selectedCommand == null){
				System.out.println("command '" + inputCommand + "' does not exist, enter '" + BoxCommand.HELP.getName() + "' to view available commands");
			}
			else{
				return selectedCommand;
			}
		}
	}
	
	private static void executeCommand(BoxCommand command){
		switch(command){
			case BoxCommand.OPEN:
				open();
				break;
			case BoxCommand.CLOSE:
				close();
				break;
			case BoxCommand.LOCK:
				lock();
				break;
			case BoxCommand.UNLOCK:
				unlock();
				break;
			case BoxCommand.HELP:
				showCommands();
				break;
			case BoxCommand.EXIT:
				exit();
				break;
		}
	}
	
	private static void showCommands(){
		for(BoxCommand command : BoxCommand.values()){
			System.out.println("  - " + command.getName() + ": " + command.getDescription());
		}
	}

	private static void exit(){
		_running = false;
	}
}