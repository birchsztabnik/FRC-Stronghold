package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

public class GrabBall extends Command {
	@Override
	protected void initialize() {
		
	}
	public GrabBall()
	{
		requires(Robot.intake_wheels);
	}

	@Override
	protected void execute() {
		Robot.intake_wheels.grab_ball(Robot.oi.xbox_controller2);

	}

    public void start_command(){
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.intake_wheels.stop_wheel();
	}

	@Override
	protected void interrupted() {

	}
}