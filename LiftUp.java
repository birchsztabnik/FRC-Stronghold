package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftUp extends Command {

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		Robot.climb.lift();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.climb.stop();
	}

	@Override
	protected void interrupted() {

	}
}