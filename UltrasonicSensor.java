package org.usfirst.frc.team2875.robot.subsystems;
import org.usfirst.frc.team2875.robot.commands.StartUltrasonic;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class UltrasonicSensor extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new StartUltrasonic());
	}
	    private final double min_voltage = 3.3D;	  
	    private final double voltage_range = 2.2D; //voltage_range = 5.5 - min_voltage
	    private final double min_distance = 6.0D;  
	    private double distance_range;
	    private double scale_factor;
	    AnalogInput channel;
	    
	    public UltrasonicSensor(int _channel) {
	        channel = new AnalogInput(_channel);
			distance_range = 254.0 - min_distance;
			scale_factor = distance_range/voltage_range;	
	    }
	    
	    public double get_range_inch() {
	        double distance;
	        distance = channel.getVoltage()*scale_factor;
	        return distance;
	    }

}
