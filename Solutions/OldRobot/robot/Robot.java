package robot;

import commands.RobotCommand;

import java.util.List;

public class Robot {
	public static final int COMMAND_COUNT = 3;
	
    private int x;
    private int y;
    private boolean isPowered;

	private final RobotCommand[] commands = new RobotCommand[COMMAND_COUNT];

	public Robot(List<RobotCommand> inputCommands) {
        if (inputCommands.size() != COMMAND_COUNT) {
			throw new IllegalArgumentException("Error: Need three commands.");
		}
		
		for(int i = 0; i < inputCommands.size(); i++){
			commands[i] = inputCommands.get(i);
		}
    }
	
    public void moveX(int step) {
        if (isPowered) x += step;
    }

    public void moveY(int step) {
        if (isPowered) y += step;
    }
	
	public boolean isPowered(){
		return isPowered;
	}

    public void setPowered(boolean powered) {
        isPowered = powered;
    }

    public void executeCommands() {
        for (RobotCommand command : commands) {
            command.run(this);
            System.out.printf("[%d %d %b]\n", x, y, isPowered);
        }
    }
}