package org.usfirst.frc.team2875.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	private Solenoid Solenoid2;
	private double start_time;
	
    public Pneumatics(){
    	Solenoid2 = new Solenoid(1,7);
    }
	
	public void initDefaultCommand() {
    }
    
    public void shoot()
    {
    	start_time=System.currentTimeMillis();
    	while(System.currentTimeMillis() - start_time <= 1000){
    		Solenoid2.set(true);
		}
    	Solenoid2.set(false);	
    }   
}