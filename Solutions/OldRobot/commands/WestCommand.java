package commands;

import robot.Robot;

public class WestCommand implements RobotCommand{
	public void run(Robot robot){
		if(robot.isPowered()){
			robot.moveX(-1);
		}
	}
}