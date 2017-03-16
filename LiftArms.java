package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

public class LiftArms extends Command {

	public LiftArms(){
		requires(Robot.arms);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.arms.move_arms(Robot.oi.xbox_controller2);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.arms.stop_arms();
	}

	@Override
	protected void interrupted() {
	}
}