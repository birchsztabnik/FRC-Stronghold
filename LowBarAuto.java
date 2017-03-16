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

public class LowBarAuto extends CommandGroup {
	
	public LowBarAuto()
	{
		
		addParallel(new DriveXFeet(16,.6D));
		addParallel(new LowerArmsAuto());
		addSequential(new TurnTo(60,Direction.RIGHT));
		addSequential(new TimeOut(1));
		
		addSequential(new AutoAlign());
		addSequential(new TimeOut(0.2D));
		//addSequential(new AutoAlign());
		addSequential(new OpenSolenoid());
	}

}
