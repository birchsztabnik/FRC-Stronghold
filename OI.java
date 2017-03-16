package org.usfirst.frc.team2875.robot;

import org.usfirst.frc.team2875.robot.commands.AutoShooter;
import org.usfirst.frc.team2875.robot.commands.CreepLeft;
import org.usfirst.frc.team2875.robot.commands.CreepRight;
import org.usfirst.frc.team2875.robot.commands.LiftDown;
import org.usfirst.frc.team2875.robot.commands.LiftScissor;
import org.usfirst.frc.team2875.robot.commands.LiftUp;
import org.usfirst.frc.team2875.robot.commands.LowerScissor;
import org.usfirst.frc.team2875.robot.commands.OpenSolenoid;
import org.usfirst.frc.team2875.robot.commands.ToggleBinary;
import org.usfirst.frc.team2875.robot.commands.ToggleView;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The operator interface of the robot, it has been simplified from the real
 * robot to allow control with a single PS3(Xbox) joystick. As a result, not all
 * functionality from the real robot is available.
 */
public class OI{
	public Joystick xbox_controller;
	public Joystick xbox_controller2;
	public Button x_openSol;
	public Button x_openSol2;
	public Button x_closeSol;
	public Button x_auto_shoot;
	public Button x_creep_right;
	public Button x_creep_left;
	public Button x_toggle_binary;
	public Button x_toggle_binary2;
	public Button x_toggle_view;
	public Button x_toggle_view2;
	//public Button x_lift_up;
	//public Button x2_lift_scissor;
	
	public OI() {
		xbox_controller = new Joystick(0);
		xbox_controller2 = new Joystick(1);
		//xbox_controller2.setRumble(RumbleType.kLeftRumble, 1);
		//xbox_controller2.setRumble(RumbleType.kRightRumble, 1);
        x_creep_left = new JoystickButton(xbox_controller,5);
      //  x_lift_up = new JoystickButton(xbox_controller2,1);
        x_creep_right = new JoystickButton(xbox_controller,6);
		x_openSol = new JoystickButton(xbox_controller,3);//chg
		x_openSol2 = new JoystickButton(xbox_controller2,3);//chg
		x_toggle_binary = new JoystickButton(xbox_controller,8);
		x_toggle_binary2 = new JoystickButton(xbox_controller2,8);
		x_toggle_view = new JoystickButton(xbox_controller, 7);
		x_toggle_view2 = new JoystickButton(xbox_controller2, 7);
		x_auto_shoot = new JoystickButton(xbox_controller,4);//chg
		//x2_lift_scissor = new JoystickButton(xbox_controller2, 2);
		
		//x2_lift_scissor.whileHeld(new LiftScissor());
		//x2_lift_scissor.whenReleased(new LowerScissor());
		x_toggle_binary.whenPressed(new ToggleBinary());
		x_toggle_binary2.whenPressed(new ToggleBinary());
		x_creep_left.whileHeld(new CreepLeft());
		x_creep_right.whileHeld(new CreepRight());
		x_openSol.whenPressed(new OpenSolenoid());		
		x_openSol2.whenPressed(new OpenSolenoid());	
		x_auto_shoot.whenPressed(new AutoShooter());
		//x_lift_up.whileHeld(new LiftUp());
		//x_lift_down.whileHeld(new LiftDown());
		x_toggle_view.whenPressed(new ToggleView());
		x_toggle_view2.whenPressed(new ToggleView());
	}

	public Joystick getJoystick() {
		return xbox_controller;
	}
	public Joystick getJoystick2() {
		return xbox_controller2;
	}
}
