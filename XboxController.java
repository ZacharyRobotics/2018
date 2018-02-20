package org.usfirst.frc.team6489.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController extends Joystick {
	
	public Button a;
	public Button b;
	public Button x;
	public Button y;
	public Button start;
	public Button select;
	public Button leftBumper;
	public Button rightBumper;
	public Button leftJoyButton;
	public Button rightJoyButton;
	
	public XboxController(int port) {
		// Port 1 is the first USB port on the left side of my laptop.
		super(port);
		a = new JoystickButton(this, 1);
		b = new JoystickButton(this, 2);
		x = new JoystickButton(this, 3);
		y = new JoystickButton(this, 4);
		leftBumper = new JoystickButton(this, 5);
		rightBumper = new JoystickButton(this, 6);
		select = new JoystickButton(this, 7);
		start = new JoystickButton(this, 8);
		leftJoyButton = new JoystickButton(this, 9);
		rightJoyButton = new JoystickButton(this, 10);
	}
	
	/**
	 * Gets the Y axis of the left Xbox joystick.
	 **/
	public double getY1() {
		return getRawAxis(1);
	}
	
	/**
	 * Gets the X axis of the right Xbox joystick.
	 **/
	public double getX2() {
		return getRawAxis(4);
	}
	
	/**
	 * Gets the value of the left trigger.
	 **/
	public double getLeftTrigger() {
		return getRawAxis(2);
	}
	
	/**
	 * Gets the value of the right trigger.
	 **/
	public double getRightTrigger() {
		return getRawAxis(3);
	}
}
