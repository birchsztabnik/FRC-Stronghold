package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Gearbox;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climb extends Subsystem { 
	private Gearbox winch;
	//private Solenoid solenoid;
	private DoubleSolenoid solenoid;
	private double start_time;

	public Climb(){
		winch = new Gearbox(6,7);
    	solenoid = new DoubleSolenoid(1, 0, 1);
    	
	}
	@Override
	protected void initDefaultCommand() {
	}
	public void lift(){
		winch.set_speed(1);	
	}
	
	public void lift_down(){
		winch.set_speed(0);	
	}
	
	public void lift_scissor(){
		solenoid.set(DoubleSolenoid.Value.kForward);
	}
	public void lift_down_scissor(){
		solenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void stop(){
		winch.stop();
	}
}