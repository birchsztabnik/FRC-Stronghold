package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

public class TurnTo extends Command {
	public double theta;
	public Direction direction;
	
	public TurnTo(double theta, Direction direction)
	{
		requires(Robot.drivetrain);
		this.theta=theta;
		this.direction=direction;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		if(direction==Direction.RIGHT)
			Robot.drivetrain.turbo(.5, .5);
		else
			Robot.drivetrain.turbo(-.5, -.5);
	}

	@Override
	protected boolean isFinished() {	
		if(direction==Direction.RIGHT&&Robot.gyroscope.get_heading_neg()<this.theta)
			return false;
		else if(direction==Direction.LEFT&&Robot.gyroscope.get_heading_neg()>this.theta)
			return false;
		else
			return true;
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
