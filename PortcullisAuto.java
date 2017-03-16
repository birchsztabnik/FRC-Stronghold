package org.usfirst.frc.team2875.robot.autocommands;

import org.usfirst.frc.team2875.robot.commands.DriveXFeet;
import org.usfirst.frc.team2875.robot.commands.LiftArmsAuto;
import org.usfirst.frc.team2875.robot.commands.LowerArmsAuto;
import org.usfirst.frc.team2875.robot.commands.TimeOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PortcullisAuto extends CommandGroup {
	public PortcullisAuto()
	{
		addParallel(new DriveXFeet(4.0,.7D));
		addParallel(new LowerArmsAuto());
		addSequential(new TimeOut(2.0D));
		addSequential(new DriveXFeet(1.0D,.65D));
		addParallel(new DriveXFeet(2.0D,.65D));
		addParallel(new LiftArmsAuto());		
	}
}