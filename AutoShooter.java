package org.usfirst.frc.team2875.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoShooter extends CommandGroup {

	public AutoShooter() {
		setTimeout(5);
		addSequential(new AutoAlign());
		//addSequential(new OpenSolenoid());
	}
}