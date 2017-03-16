package org.usfirst.frc.team2875.robot.autocommands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.DriveXFeet;
import org.usfirst.frc.team2875.robot.commands.TurnTo;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RampartAuto extends CommandGroup {
	public RampartAuto()
	{
		addSequential(new DriveXFeet(4,0.65D));
		addSequential(new DriveXFeet(4,0.4D));
		if(Robot.gyroscope.get_heading_neg() > 0.0D){
			addSequential(new TurnTo(0.0D, Direction.LEFT));	
		}else{
			addSequential(new TurnTo(0.0D, Direction.RIGHT));	

		}
	}

}
