package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CreepRight extends Command {

    public CreepRight() {
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.pneumatics); 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    public void start_command(){
		initialize();
	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.turbo(0.3,0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
