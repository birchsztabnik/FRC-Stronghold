package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.commands.GrabBall;
import org.usfirst.frc.team2875.robot.commands.LiftArms;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallGrabber extends Subsystem {


	private VictorSP wheel_motor;
	public BallGrabber()
	{
		wheel_motor = new VictorSP(5);
		
	}
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new GrabBall());
		
	}
	
	public void grab_ball(Joystick joystick){
		//SmartDashboard.putNumber("GrabBall", wheel_motor.get());
		wheel_motor.set(joystick.getRawAxis(3)-joystick.getRawAxis(2));
	}
	public void stop_wheel(){
		wheel_motor.stopMotor();
	}

}
