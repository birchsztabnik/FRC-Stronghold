package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveXFeet extends Command {

	private double distance;
	private double speed;
	public DriveXFeet(double dist, double speed)
	{
		distance=dist;
		this.speed=speed;
		Robot.drivetrainencoders.resetEncoders();
	}
	@Override
	protected void initialize() {

		Robot.drivetrainencoders.resetEncoders();
	}

	@Override
	protected void execute() {
		Robot.drivetrain.AutoStraightDrive(this.speed);
		SmartDashboard.putNumber("gyr0", Robot.gyroscope.get_heading());
	}

	@Override
	protected boolean isFinished() {
		if(Math.abs(Robot.drivetrainencoders.getLeftEncoder())>distance)
			return true;
		else
			return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
