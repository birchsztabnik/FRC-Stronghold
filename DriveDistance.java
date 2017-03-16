package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
	private double start_distance;
	private double distance_to_go;
	
	public DriveDistance(double distance_to_go){
		this.distance_to_go = distance_to_go;
	}
	
	@Override
	protected void initialize() {
		start_distance = Robot.sonar.get_range_inch();
	}

	@Override
	protected void execute() {
		if((Robot.sonar.get_range_inch()-(Robot.sonar.get_range_inch()-distance_to_go))<distance_to_go){
			Robot.drivetrain.turbo(1, 1);
		}else{
			Robot.drivetrain.stop();
			end();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
