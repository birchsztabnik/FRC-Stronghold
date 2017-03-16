package org.usfirst.frc.team2875.robot.autocommands;

import org.usfirst.frc.team2875.robot.commands.AutoAlign;
import org.usfirst.frc.team2875.robot.commands.DrivePath;
import org.usfirst.frc.team2875.robot.commands.DriveXFeet;
import org.usfirst.frc.team2875.robot.commands.LowerArmsAuto;
import org.usfirst.frc.team2875.robot.commands.OpenSolenoid;
import org.usfirst.frc.team2875.robot.commands.TimeOut;
import org.usfirst.frc.team2875.robot.commands.TurnTo;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPos5 extends CommandGroup {
	public AutoPos5(){
		addSequential(new DriveXFeet(1.0D,0.65D));
		addSequential(new TurnTo(-40.0D,Direction.LEFT));
		addSequential(new LowerArmsAuto());
		addSequential(new TimeOut(1.0D));
		addSequential(new AutoAlign());
		addSequential(new TimeOut(1.0D));
		addSequential(new AutoAlign());
		addSequential(new TimeOut(1));
		addSequential(new OpenSolenoid());

	}
}
