package commands;

import robot.Robot;

public class OffCommand implements RobotCommand{
	public void run(Robot robot){
		robot.setPowered(false);
	}
}