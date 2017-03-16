package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.subsystems.UltrasonicSensor;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@Deprecated
public class StartUltrasonic extends Command {
	
	public StartUltrasonic()
	{
		requires(Robot.sonar);
	}

	@Override
	protected void initialize() {
		//nah
	}

	@Override
	protected void execute() {
		//SmartDashboard.putNumber("sonar",Robot.sonar.get_range_inch());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
