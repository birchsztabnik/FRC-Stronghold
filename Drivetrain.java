package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Gearbox;
import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.DriveWithJoystick;

import org.usfirst.frc.team2875.robot.util.Direction;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The DriveTrain subsystem controls the robot's chassis and reads in
 * information about it's speed and position.
 * 
 * @author Birch
 */
public class Drivetrain extends Subsystem {
	// Subsystem devices
	private Gearbox left_gearbox, right_gearbox;
	private boolean in_position, is_aligned;
	private double max_linear = 0;
	private double last_linear = 0;
	private double accel_rate = 1.05;
	private double decel_rate = .95;
	private double straight_val = 0;

	public Drivetrain() {

		left_gearbox = new Gearbox(0,1);
		right_gearbox = new Gearbox(2,3);
		in_position = false;
		is_aligned = false;
		//SmartDashboard.putNumber("accel rate", accel_rate);
		//SmartDashboard.putNumber("decel rate", decel_rate);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	/**
	 * Gathers info from the joystick prior to moving
	 * Uses the left joystick and the triggers
	 * @param joy
	 */
	@Deprecated
	public void input_no(Joystick joy) {
		double linear = -joy.getRawAxis(1);
		double linear_abs = Math.abs(linear);
		double left = joy.getRawAxis(2);
		double left_abs = Math.abs(left);
		double right = joy.getRawAxis(3);
		double right_abs = Math.abs(right);
		
		if (linear_abs < .2){
			straight_val = Robot.gyroscope.get_heading();
		}
			
		drive(acceleration(linear,linear_abs) ,left ,right );
		//drive(linear,left,right);
	}
	//for jhh
	//test
	public void input(Joystick joy){
		double linear = -joy.getRawAxis(1);
		double linear_abs = Math.abs(linear);
		double right = joy.getRawAxis(0);
		double right_abs = Math.abs(right);
		if (linear_abs < .142){
			straight_val = Robot.gyroscope.get_heading();
		}
		if (linear_abs > Robot.deadzone || right_abs > Robot.deadzone) {
			drive(linear_abs > Robot.deadzone ? linear : 0,
					0,
					right_abs > Robot.deadzone ? right : 0);
		} else {
			stop();
		}
	}
	/**
	 * Drives the robot with the left joystick
	 * @param joy xbox controller
	 */
	public void input_good(Joystick joy) {
		double linear = -joy.getRawAxis(1);
		double linear_abs = Math.abs(linear);
		double left = joy.getRawAxis(2);
		double left_abs = Math.abs(left);
		double right = joy.getRawAxis(3);
		double right_abs = Math.abs(right);
		if (linear_abs < .142){
			straight_val = Robot.gyroscope.get_heading();
		}
		if (linear_abs > Robot.deadzone || left_abs > Robot.deadzone || right_abs > Robot.deadzone) {
			drive(linear_abs > Robot.deadzone ? linear : 0,
					left_abs > Robot.deadzone ? left : 0,
					right_abs > Robot.deadzone ? right : 0);
		} else {
			stop();
		}
	}
	
	
	
	public void drive_angle(double cur_heading, double theta, Direction d){
		
		double diff = Math.abs(theta-cur_heading);
		
		if(diff<5){
			forward_spin(0.4D, d);
		}
		else if(diff>5 && diff<10){
			forward_spin(0.6D, d);
		
		}
		else if(diff>10&&diff<20){
			forward_spin(0.8D, d);
		}
		else
		{
			forward_spin(1.0D, d);
			
		}
		
		
	}
	
	public void forward_spin(double spin_val, Direction dir)
	{
		
		
		if(dir==Direction.RIGHT){
			//right_gearbox.set_speed((-0.5D + ((Math.abs(get_heading() - theta))/180)));
			right_gearbox.set_speed(-1+spin_val);
			left_gearbox.set_speed(spin_val);
		}else{
			right_gearbox.set_speed(-spin_val);
			//left_gearbox.set_speed((-0.5D + ((Math.abs(get_heading() - theta))/180)));
			left_gearbox.set_speed(1-spin_val);
		}
		
	}
	
	@Deprecated
	public double acceleration(double linear, double linear_abs){
		//accel_rate = SmartDashboard.getNumber("accel rate");
		//decel_rate = SmartDashboard.getNumber("decel rate");
		//SmartDashboard.putNumber("Joystick Input", linear);
		max_linear = linear_abs != max_linear ? linear : max_linear;
		if (linear_abs < .2){
			max_linear = 0;
			stop();
		}
		//SmartDashboard.putNumber("max linear", max_linear);
		if (linear_abs < .2){
			last_linear = 0;
			//SmartDashboard.putNumber("rear right", 0); 
			//SmartDashboard.putNumber("rear left", 0);
		}else if (last_linear == 0 && linear_abs > .2){
			last_linear = linear/2;
		}
		if( Math.abs(last_linear) < Math.abs(max_linear) && Math.abs(last_linear)*1.1 <= Math.abs(max_linear)){
			last_linear *= accel_rate;
		}else if (Math.abs(last_linear) > Math.abs(max_linear)){
			last_linear *= decel_rate;
		}else if (Math.abs(last_linear)*1.1 > Math.abs(max_linear)){
			last_linear = max_linear;
		}
		return last_linear;
	}

	/**
	 * Zoom Zoom got to go fast
	 * 
	 * @param linear
	 * @param left
	 * @param right
	 */

	//set speeds for motors based off of acceleration values and joystick inputs
	private void drive(double linear, double left, double right) {
		//linear = linear /4;
		//left = left /2;
		//right = right /2;
		double spin = right - left;
		if (Math.abs(linear) < .2){
			straight_val = Robot.gyroscope.get_heading();		
		}
		if (spin == 0 && linear != 0) {// Move without spin
			if (Robot.gyroscope.get_heading()+1 < straight_val){
				right_gearbox.set_speed((-linear + ((Math.abs(Robot.gyroscope.get_heading() - straight_val))/180)));
				left_gearbox.set_speed(linear);
			}else if (Robot.gyroscope.get_heading()-1 > straight_val){
				right_gearbox.set_speed(-linear);
				left_gearbox.set_speed(linear - ((Math.abs(Robot.gyroscope.get_heading() - straight_val))/180));	
			}else{
				right_gearbox.set_speed(-linear);
				left_gearbox.set_speed(linear);
			}
		} else if (spin != 0 && linear == 0) { // Spin in place
			right_gearbox.set_speed(spin);
			left_gearbox.set_speed(spin);
			straight_val = Robot.gyroscope.get_heading();
		} else if (spin > 0 && linear != 0) {// turn right
			left_gearbox.set_speed(linear);
			straight_val = Robot.gyroscope.get_heading();
			right_gearbox.set_speed(-linear+((spin*spin)/1.0D));
		} else if (spin < 0 && linear != 0) {// turn left
			left_gearbox.set_speed(linear-((spin*spin)/1.0D));
			right_gearbox.set_speed(-linear);
			straight_val = Robot.gyroscope.get_heading();

		}
		SmartDashboard.putNumber("rear right",right_gearbox.get());
		SmartDashboard.putNumber("rear left", left_gearbox.get());
	}

	/**
	 * V-TEC yo
	 * 
	 * @param left
	 *            speed
	 * @param right
	 *            speed
	 */
	public void turbo(double left, double right) {
		right_gearbox.set_speed(right);
		left_gearbox.set_speed(left);
		SmartDashboard.putNumber("rear right", right);
		SmartDashboard.putNumber("rear left", left);
	}
	public void AutoStraightDrive(double speed)
	{
		
		if(Math.abs(Robot.drivetrainencoders.getLeftEncoder())>Math.abs(Robot.drivetrainencoders.getRightEncoder()))
			turbo(speed-.1,-speed);
		else if(Math.abs(Robot.drivetrainencoders.getRightEncoder())>Math.abs(Robot.drivetrainencoders.getLeftEncoder()))
			turbo(speed,-speed+.1);
		else
			turbo(speed,-speed);
		
	}

	/**
	 * mad tork 2 the rw
	 * 
	 * 
	 * @2Fast
	 * @2Furious
	 * @param left
	 * @param right
	 */
	@Deprecated
	public void twin_turbo(double left, double right) {
		double boost = 2.5;
		left *= boost;
		right *= boost;
		left_gearbox.set_speed(right);
		right_gearbox.set_speed(-left);
	}

	/**
	 * Stops the drivetrain from moving.
	 */
	public void stop() {
		right_gearbox.stop();
		left_gearbox.stop();
		SmartDashboard.putNumber("rear right", 0.0D);
		SmartDashboard.putNumber("rear left", 0.0D);
	}
	/**
	 * Aligns robot to goal by finding angle
	 * @see drive_degrees
	 */
	@Deprecated
	public void super_align(){
		double theta = Robot.webcamera.get_theta();
		SmartDashboard.putNumber("SuperAlignTheta", theta);
		drive_degrees(theta);
		SmartDashboard.putBoolean("Aligned", true);
		is_aligned = true;

	}
	
	/**
	 * Rotates the robot by an amount of degrees
	 * @param degrees angle in degrees
	 */
	@Deprecated
	public void drive_degrees(double degrees){
		double currentAngle=Robot.gyroscope.getHeading();
		int direction = degrees>0?1:-1;
		
		if (direction == 1){
			
			while(Robot.gyroscope.getHeading()<currentAngle+degrees){
				Robot.drivetrain.turbo(-0.3D,0D);			
			}
			Robot.drivetrain.stop();
		} else if (direction == -1){
			while(Robot.gyroscope.getHeading() > currentAngle-degrees){
				Robot.drivetrain.turbo(.3D,0D);			
			}
			Robot.drivetrain.stop();
		}
	}
	@Deprecated
	public void align() {
		double rect_left=Robot.webcamera.getRectLeft();
		if(SmartDashboard.getBoolean("IsGoal")){
		if (rect_left > 230) {
			Robot.drivetrain.turbo(0.2D, 0D);
		} else if (rect_left < 245) {
			Robot.drivetrain.turbo(0D, -.2D);
		} else {
			Robot.drivetrain.stop();
			is_aligned = true;
			SmartDashboard.putBoolean("Aligned", true);
		}}else
		{
			Robot.drivetrain.stop();
		}	
	}

	public void position(){
		double distance = Robot.webcamera.getRectLeft();
		if(SmartDashboard.getBoolean("IsGoal")){
		if (distance > 10.0D&& distance <20.0D) {
			Robot.drivetrain.turbo(0.3D, -0.3D);
			Robot.drivetrain.stop();
		} else if (distance < 9.0D) {
			Robot.drivetrain.turbo(-0.3D, 0.3D);
			Robot.drivetrain.stop();
		} else{
			Robot.drivetrain.stop();
			in_position = true;
			SmartDashboard.putBoolean("In Position", true);
		}}
	}
	

	public boolean get_position_status() {
		return in_position;
	}

	public boolean get_alignment_status() {
		return is_aligned;
	}

	public void reset_alignment() {
		is_aligned = false;
		SmartDashboard.putBoolean("Aligned", false);
	}
	
	public void reset_position(){
		in_position = false;
		SmartDashboard.putBoolean("In Position", false);
	}
	
	public boolean isGyroCalibrating()
	{
		return Robot.gyroscope.isCalibrating();
	}
	
	public void calibrateGyro()
	{
		Robot.gyroscope.calibrate();
	}
}
