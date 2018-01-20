package org.usfirst.frc.team6489.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick left;
	public static JoystickButton leftControls;
	public static Joystick right;
	public static JoystickButton rightControls;
	public static XboxController xbox;
	
	// TODO: Test if these works as static!
	public static Button forkliftUp;
	public static Button forkliftDown;
	//public static double cubeIn;
	//public static double cubeOut;
	
	/**
	 * Initializes all the buttons and their actions.
	 **/
	public void initControls() {
		xbox = new XboxController(0);
		// TODO: Is this correct?
		left = new Joystick(1);
		right = new Joystick(2);
		
		// TODO: Is this necessary?
		InternalButton none = new InternalButton(); // Effectively a button that is eternally not-pressed
		
		// TODO: Test if this is correct.
		leftControls = new JoystickButton(left, 11);
		rightControls = new JoystickButton(right, 12);
		
		//cubeIn = xbox.getLeftTrigger(); // TODO: Set one or both bumpers as cube out? Depends on if one or two wheels are used.
		//cubeOut = xbox.getRightTrigger();
		forkliftUp = xbox.leftBumper;
		forkliftDown = xbox.rightBumper;
	}
	
	public OI() {
		initControls();
	}
}