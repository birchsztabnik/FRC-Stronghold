package org.usfirst.frc.team2875.robot;
import org.usfirst.frc.team2875.robot.autocommands.AutoPos2;
import org.usfirst.frc.team2875.robot.autocommands.AutoPos3;
import org.usfirst.frc.team2875.robot.autocommands.AutoPos4;
import org.usfirst.frc.team2875.robot.autocommands.AutoPos5;
import org.usfirst.frc.team2875.robot.autocommands.ChevalAuto;
import org.usfirst.frc.team2875.robot.autocommands.LowBarAuto;
import org.usfirst.frc.team2875.robot.autocommands.MoatAuto;
import org.usfirst.frc.team2875.robot.autocommands.Nothing;
import org.usfirst.frc.team2875.robot.autocommands.PortcullisAuto;
import org.usfirst.frc.team2875.robot.autocommands.RampartAuto;
import org.usfirst.frc.team2875.robot.autocommands.RockWallAuto;
import org.usfirst.frc.team2875.robot.autocommands.RoughAuto;
import org.usfirst.frc.team2875.robot.subsystems.Arms;
import org.usfirst.frc.team2875.robot.subsystems.BallGrabber;
import org.usfirst.frc.team2875.robot.subsystems.Camera;
import org.usfirst.frc.team2875.robot.subsystems.Climb;
import org.usfirst.frc.team2875.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2875.robot.subsystems.Encoders;
import org.usfirst.frc.team2875.robot.subsystems.Gyroscope;
import org.usfirst.frc.team2875.robot.subsystems.Pneumatics;
import org.usfirst.frc.team2875.robot.subsystems.UltrasonicSensor;
import org.usfirst.frc.team2875.robot.util.Dejitter;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * @author Birch 
 * @author Logan
 */

public class Robot extends IterativeRobot {
	public static Gyroscope gyroscope;
	public static Arms arms;
	public static BallGrabber intake_wheels;
	public static OI oi;
	public static Camera webcamera;
	public static Pneumatics pneumatics;
	public static UltrasonicSensor sonar;
	public static Drivetrain drivetrain;
	public static Encoders drivetrainencoders;
	public static final double deadzone = 0.2D;
	public static Command auto_command;
	public static Command auto_command2;
	public static SendableChooser auto_select;
	public static SendableChooser auto_select_pos;
	//public static Gyro gyro;
	public static double curAngle;
	public static boolean gyroCalibrating;
	public static boolean lastGyroCalibrating;
	public static double lastAngle;
	public static Dejitter gyroDriftDetector;
	public static boolean matchStarted=false;
	public static int gyroReinits;
	private static boolean camera_exists;
	public static boolean isEnabled;
	public static boolean has_ran;
	public static Climb climb;
	private static boolean first_run;
	
	public void robotInit() {
		first_run = true;
		try{
			webcamera = new Camera();
			camera_exists = true;
		}catch(Exception e){
			camera_exists = false;
		}
		
		pneumatics = new Pneumatics();
		sonar = new UltrasonicSensor(0);
		drivetrain = new Drivetrain();
		oi = new OI();
		gyroDriftDetector = new Dejitter(10);
		gyroReinits=0;
		arms = new Arms();
		intake_wheels=new BallGrabber();
		drivetrainencoders=new Encoders();
		gyroscope=new Gyroscope();
		gyroscope.startThread();
		climb = new Climb();
		
		
		//auto mode
		auto_select = new SendableChooser();
		auto_select.addDefault("Nothing", new Nothing());
		auto_select.addObject("Moat", new MoatAuto());
		auto_select.addObject("Rock Wall", new RockWallAuto());
		auto_select.addObject("Cheval",new ChevalAuto());
		auto_select.addObject("Rampart", new RampartAuto());
		auto_select.addObject("Rough Terrain", new RoughAuto());
		auto_select.addObject("Low Bar", new LowBarAuto());
		auto_select.addObject("Portcullis", new PortcullisAuto());
		SmartDashboard.putData("Autonomous mode chooser", auto_select);
		
		auto_select_pos=new SendableChooser();
		auto_select_pos.addDefault("Nothing", new Nothing());
		auto_select_pos.addObject("2", new AutoPos2());
		auto_select_pos.addObject("3", new AutoPos3());
		auto_select_pos.addObject("4", new AutoPos4());
		auto_select_pos.addObject("5", new AutoPos5());
		SmartDashboard.putData("Autonomous Position Chooser", auto_select_pos);
		
	}
	
	public void autonomousInit() {
		isEnabled=isEnabled();
		drivetrainencoders.resetEncoders();
		gyroscope.reset();
		auto_command = (Command) auto_select.getSelected();
		auto_command.start();
		auto_command2= (Command) auto_select_pos.getSelected();
		auto_command2.start();
		has_ran=false;
	}

	// This function is called periodically during autonomous
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
		
		if(camera_exists){
			webcamera.execute();
		}
		if(!auto_command.isRunning()&&has_ran==false){
			auto_command2.start();
			has_ran=true;
		}
	}

	public void teleopInit() {
		if(first_run){
			webcamera.enable_view_camera();	
			first_run = false;
		}
		isEnabled=isEnabled();
		if(auto_command!=null)
			auto_command.cancel();
		if(auto_command2!=null)
			auto_command2.cancel();
	}

	// This function is called periodically during operator control
	public void teleopPeriodic() {
		
		if (auto_command != null) {
			auto_command.cancel();
		}
		
		Scheduler.getInstance().run();
		log();
		if(camera_exists){
			webcamera.execute();
		}
		SmartDashboard.putNumber("gyr0", Robot.gyroscope.get_heading_neg());
		SmartDashboard.putNumber("Left Encoder", Robot.drivetrainencoders.getLeftEncoder());
		SmartDashboard.putNumber("Right Encoder", Robot.drivetrainencoders.getRightEncoder());
		
	}

	// This function called periodically during test mode
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void disabledInit() {
		//Robot.shooter.unlatch();
		isEnabled=isEnabled();
		
	}
	
	// This function is called periodically while disabled
	public void disabledPeriodic() {

		log();
		curAngle = gyroscope.get_heading();
		gyroCalibrating = drivetrain.isGyroCalibrating(); //this is a call into ADXRS453Gyro.isCalibrating();

		if (lastGyroCalibrating && !gyroCalibrating) {
			//if the gyro is done calibrating, reset
			gyroDriftDetector.reset();
			curAngle = gyroscope.get_heading();
			System.out.println("Finished auto-reinit gyro");
		} else if (gyroDriftDetector.update(Math.abs(curAngle - lastAngle) > (0.75 / 50.0))
				&& !matchStarted && !gyroCalibrating) {
			//&& gyroReinits < 3) {
			gyroReinits++;
			System.out.println("!!! Sensed drift, about to auto-reinit gyro ("+ gyroReinits + ")");
			drivetrain.calibrateGyro();
		}

		lastAngle = curAngle;
		lastGyroCalibrating = gyroCalibrating;
		
	}
	
	/**
	 * Log interesting values to the SmartDashboard.
	 */
	private void log() {
		
	}
}
