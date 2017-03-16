package org.usfirst.frc.team2875.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Encoders extends Subsystem {

	private Encoder left_encoder;
	private Encoder right_encoder;
	
	
	public Encoders()
	{
		left_encoder = new Encoder(3,4, true, EncodingType.k4X); 		
		left_encoder.setDistancePerPulse(0.0022);
		right_encoder= new Encoder(1,2,true, EncodingType.k4X); 		
		right_encoder.setDistancePerPulse(0.0022);	
	}
	
	public void resetEncoders()
	{
		left_encoder.reset();
		right_encoder.reset();
	}
	
	public Double getLeftEncoder()
	{
		return left_encoder.getDistance();
		
	}
	public Double getRightEncoder()
	{
		return right_encoder.getDistance();
	}

	@Override
	protected void initDefaultCommand() {		
		
	}
}