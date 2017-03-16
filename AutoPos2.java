package org.usfirst.frc.team2875.robot.autocommands;

import org.usfirst.frc.team2875.robot.commands.AutoAlign;
import org.usfirst.frc.team2875.robot.commands.DriveXFeet;
import org.usfirst.frc.team2875.robot.commands.LowerArmsAuto;
import org.usfirst.frc.team2875.robot.commands.OpenSolenoid;
import org.usfirst.frc.team2875.robot.commands.TimeOut;
import org.usfirst.frc.team2875.robot.commands.TurnTo;


import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPos2 extends CommandGroup {

	public AutoPos2()
	{
		addSequential(new DriveXFeet(2.0D,0.65D));
		addSequential(new TurnTo(30,Direction.RIGHT));
		addSequential(new LowerArmsAuto());
		addSequential(new AutoAlign());
		addSequential(new TimeOut(1.0D));
		addSequential(new AutoAlign());
		addSequential(new TimeOut(1.0D));
		//SaddSequential(new OpenSolenoid());
		//addSequential(new OpenSolenoid());
		
	}
}
