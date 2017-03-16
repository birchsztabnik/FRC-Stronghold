package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTime extends Command {
	private double time;
	private double speed;
	public DriveTime(double time, double speed){
		this.time = time;
		this.speed = speed;
	}
	@Override
	protected void initialize() {
		setTimeout(time);
	}

	@Override
	protected void execute() {
		if(!isFinished()){
			Robot.drivetrain.turbo(speed,speed);
		}
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
