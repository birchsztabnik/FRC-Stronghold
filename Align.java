package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
@Deprecated
public class Align extends Command {

	@Override
	protected void initialize() {
		setTimeout(5);
		Robot.drivetrain.reset_alignment();
	}
	public void start_command(){
		initialize();
	}

	@Override
	protected void execute() {
		if(!this.isTimedOut() || Robot.drivetrain.get_alignment_status()){
			Robot.drivetrain.super_align();
		}
		else{
			end();
		}
	}

	@Override
	protected boolean isFinished(){
		return this.isTimedOut()||Robot.drivetrain.get_alignment_status();
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