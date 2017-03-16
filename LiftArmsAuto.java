package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftArmsAuto extends Command {

	public LiftArmsAuto()
	{
		requires(Robot.arms);
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		setTimeout(2);
	}

	@Override
	protected void execute() {
		Robot.arms.move_arms_auto(-.65);
		//SmartDashboard.putBoolean("Limit Switch", Robot.arms.get_switch_down());
	}

	@Override
	protected boolean isFinished() {
		//return Robot.arms.get_switch_up();
		return isTimedOut();
	}

	@Override
	protected void end() {
		Robot.arms.stop_arms();
	}

	@Override
	protected void interrupted() {

	}

}
