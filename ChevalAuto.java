package org.usfirst.frc.team2875.robot.autocommands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.DriveXFeet;
import org.usfirst.frc.team2875.robot.commands.LiftArmsAuto;
import org.usfirst.frc.team2875.robot.commands.LowerArmsAuto;
import org.usfirst.frc.team2875.robot.commands.TimeOut;
import org.usfirst.frc.team2875.robot.commands.TurnTo;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChevalAuto extends CommandGroup {

	public ChevalAuto()
	{
		addSequential(new DriveXFeet(3.3,0.5D));
		addSequential(new LowerArmsAuto());
		//TEST CODE
		//addSequential(new TimeOut(3));
		//
		addSequential(new DriveXFeet(1, 0.3D));
		addParallel(new LiftArmsAuto());
		addParallel(new DriveXFeet(4, 0.4D));
		if(Robot.gyroscope.get_heading_neg() > 0.0D){
			addSequential(new TurnTo(0.0D, Direction.LEFT));	
		}else{
			addSequential(new TurnTo(0.0D, Direction.RIGHT));	
		}
	}
}
