package commands;

import robot.Robot;

public class OnCommand implements RobotCommand{
	public void run(Robot robot){
		robot.setPowered(true);
	}
}