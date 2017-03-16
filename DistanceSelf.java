package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DistanceSelf extends Command {

	@Override
	protected void initialize() {
		setTimeout(5);
		Robot.drivetrain.reset_position();
	}
	public void start_command(){
		initialize();
	}

	@Override
	protected void execute() {
		if(! isFinished()){
			Robot.drivetrain.position();
		}
		else{
			end();
		}
	}

	@Override
	protected boolean isFinished(){
		return this.isTimedOut()||Robot.drivetrain.get_position_status();
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();

	}

	@Override
	protected void interrupted() {
		end();

	}
}