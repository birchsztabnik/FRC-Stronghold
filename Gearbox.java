package org.usfirst.frc.team2875.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Gearbox {
	private SpeedController motor_1, motor_2;
	
	public Gearbox(int pwm_channel_1, int pwm_channel_2){
		motor_1 = new Talon(pwm_channel_1);
		motor_2 = new Talon(pwm_channel_2);
	}
	
	public void set_speed(double speed){
		motor_1.set(speed);
		motor_2.set(speed);
	}
	public void stop(){
		int sign = motor_1.get()>0?1:-1;
		//Braking
		//motor_1.set(sign*0.2D);
		//motor_2.set(sign*-0.2D);
		motor_1.set(0);
		motor_2.set(0);
	}
	public double get(){
		return motor_1.get();
	}
}