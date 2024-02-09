package commands;

import robot.Robot;

public class SouthCommand implements RobotCommand{
	public void run(Robot robot){
		if(robot.isPowered()){
			robot.moveY(-1);
		}
	}
}