package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAlign extends Command {

	private double currentHeading;
	private double numDegreesToMove;
	private double end_position;
	private Direction direction;
	private double boost;


	public AutoAlign(){
		boost = 1.0D;
	}

	@Override
	protected void initialize() {
		setTimeout(5);
		//Robot will now face zero
		Robot.drivetrain.reset_alignment();
		Robot.gyroscope.reset();
		//Robot can face anywhere including past 360 deg
		currentHeading=Robot.gyroscope.get_heading_neg();
		numDegreesToMove=-Robot.webcamera.get_theta();
		if(numDegreesToMove<=0){
			direction = Direction.LEFT;
			end_position=currentHeading+numDegreesToMove;
			end_position %=360;
		}else{
			direction = Direction.RIGHT;
			end_position=currentHeading+numDegreesToMove;
			end_position %=360;
		}

		SmartDashboard.putNumber("currentHeading", currentHeading);
		SmartDashboard.putNumber("numDegressToMove", numDegreesToMove);
		SmartDashboard.putNumber("endDegrees", end_position);
	}

	@Override
	protected void execute() {
		//SmartDashboard.putNumber("currentHeading", Robot.drivetrain.getHeading());
		if(Robot.arms.get_switch_down()){
			double temp = Robot.gyroscope.get_heading_neg();
			boost=1.0D;
			if(Math.abs((end_position-temp))<4D){
				boost = 0.85D;
			}

			if(direction == Direction.RIGHT)
			{
				SmartDashboard.putString("direction", "right");
				Robot.drivetrain.turbo(.3D*boost, .3D*boost);
			}
			else if(direction == Direction.LEFT)
			{
				SmartDashboard.putString("direction", "left");
				Robot.drivetrain.turbo(-.3D*boost, -.3D*boost);	
			}
		}
	}

	@Override
	protected boolean isFinished() {
		if(!Robot.arms.get_switch_down())
			return true;
		else{

			double current_direction=Robot.gyroscope.get_heading_neg();
			double err_val=Math.abs((current_direction-end_position));
			SmartDashboard.putNumber("Error Value: ",err_val);
			if(isTimedOut()){
				return true;
			}else if(direction == Direction.RIGHT && current_direction<end_position){
				return false;
			}else if(direction == Direction.RIGHT && (err_val>2)){
				direction=Direction.LEFT;
				return false;
			}else if(direction == Direction.LEFT && current_direction>end_position){
				return false;
			}else if(direction == Direction.LEFT && (err_val>2)){
				direction=Direction.RIGHT;
				return false;
			}else{
				return true;
			}
		}
	}

	@Override
	protected void end() {
		Robot.oi.xbox_controller.setRumble(RumbleType.kRightRumble, 1);
		Robot.oi.xbox_controller.setRumble(RumbleType.kLeftRumble, 1);
		Robot.drivetrain.stop();
		Robot.oi.xbox_controller.setRumble(RumbleType.kRightRumble, 0);
		Robot.oi.xbox_controller.setRumble(RumbleType.kLeftRumble, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
