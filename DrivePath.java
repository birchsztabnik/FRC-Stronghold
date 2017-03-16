package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.util.Direction;
import org.usfirst.frc.team2875.robot.util.Path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivePath extends Command {
	private Path path;
	private double theta; 
	private int theta_counter;

	public DrivePath(Direction d){
		path = new Path(d);
		theta = 0.0D;
		theta_counter = 0;
		setTimeout(5);
	}
	
	@Override
	protected void initialize() {
		Robot.drivetrainencoders.resetEncoders();
	}

	@Override
	protected void execute() {
		double cur_heading = Robot.gyroscope.get_heading();
		
		if(cur_heading<theta)
			Robot.drivetrain.drive_angle(cur_heading, theta, Direction.RIGHT);
		else
			Robot.drivetrain.drive_angle(cur_heading, theta, Direction.LEFT);
	}
	
	@Override
	protected boolean isFinished() {
		theta = path.get(theta_counter);
		SmartDashboard.putNumber("Auto Theta", theta);
		if(isTimedOut())
			return true;
		else if(theta_counter < (path.max()-1)){
			theta_counter+=1;
			return false;
		}else if (theta_counter==(path.max()-1)){
			return true;
		}else
			return true;
	}

	@Override
	protected void end() {
		////////////////////////TEST CODE
		//theta = 0.0D;
		//theta_counter = 0;
		////////////////////////END TEST CODE
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {

	}
}