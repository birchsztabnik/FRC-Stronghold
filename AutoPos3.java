package org.usfirst.frc.team2875.robot.autocommands;

import org.usfirst.frc.team2875.robot.commands.AutoAlign;
import org.usfirst.frc.team2875.robot.commands.DrivePath;
import org.usfirst.frc.team2875.robot.commands.DriveTime;
import org.usfirst.frc.team2875.robot.commands.DriveXFeet;
import org.usfirst.frc.team2875.robot.commands.LowerArmsAuto;
import org.usfirst.frc.team2875.robot.commands.OpenSolenoid;
import org.usfirst.frc.team2875.robot.commands.TimeOut;
import org.usfirst.frc.team2875.robot.commands.TurnTo;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPos3 extends CommandGroup {

	public AutoPos3()
	{
		addSequential(new DrivePath(Direction.RIGHT));
		//addSequential(new TurnTo(10.0D,Direction.LEFT));
		addSequential(new DriveXFeet(1,.4D));
		addSequential(new LowerArmsAuto());
		addSequential(new AutoAlign());
		addSequential(new TimeOut(1.0D));
		addSequential(new AutoAlign());
		addSequential(new TimeOut(1.0D));
		addSequential(new OpenSolenoid());
		
	}
}
