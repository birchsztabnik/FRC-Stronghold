package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.GrabBall;
import org.usfirst.frc.team2875.robot.commands.LiftArms;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arms extends Subsystem {
	private Talon arm_motors;
	private final double REVERSE_BOOST = 0.65D;
	private DigitalInput limit_switch_down;
	private DigitalInput limit_switch_up;
	
	public Arms(){
		arm_motors = new Talon(4);
		limit_switch_down = new DigitalInput(0);
		limit_switch_up = new DigitalInput(5);
		
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new LiftArms());
		
		
	}
	
	public void move_arms(Joystick joy){
		double speed = -joy.getRawAxis(5)*REVERSE_BOOST;
		SmartDashboard.putBoolean("Limit Switch", !limit_switch_down.get());
		
		if(speed>Robot.deadzone){//limit switch
			//if(!limit_switch_down.get()){
				arm_motors.set(speed);
			//l}
		}else if(speed<-Robot.deadzone){
			arm_motors.set(speed);
		}else{
			stop_arms();
		}
		
		//SmartDashboard.putNumber("Window Motor Values", arm_motors.get());
	}
	
	public void move_arms_auto(double value)
	{
		SmartDashboard.putBoolean("Limit Switch", !limit_switch_down.get());
		arm_motors.set(value);
	}
	
	public void stop_arms(){
		arm_motors.stopMotor();
	}
	public boolean get_switch_down(){
		return limit_switch_down.get();
	}
	public boolean get_switch_up(){
		return limit_switch_up.get();
	}
}