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
	 * Gets the X axis of the left Xbox joystick.
	 * TODO: Maybe delete this?
	 */
	public double getX1() {
		return getRawAxis(0);
	}
	
	/**
	 * Gets the Y axis of the left Xbox joystick.
	 */
	public double getY1() {
		return getRawAxis(1);
	}
	
	/**
	 * Gets the X axis of the right Xbox joystick.
	 * TODO: Maybe delete this?
	 */
	public double getX2() {
		return getRawAxis(4);
	}
	
	/**
	 * Gets the Y axis of the right Xbox joystick.
	 */
	public double getY2() {
		return getRawAxis(5);
	}
	
	/**
	 * Gets the value of the left trigger.
	 */
	public double getLeftTrigger() {
		return getRawAxis(2);
	}
	
	/**
	 * Gets the value of the right trigger.
	 */
	public double getRightTrigger() {
		return getRawAxis(3);
	}
	
	/**
	 * Gives the current Direction of the D-Pad.
	 * (null if not pressed)
	 */
	public Direction getDPad() {
		// TODO: Does this work?
		return Direction.toDirection(getPOV(0));
	}
	
	/**
	 * Makes the controller rumble.
	 */
	public void rumble(float l, float r) {
		setRumble(RumbleType.kLeftRumble, l);
		setRumble(RumbleType.kRightRumble, r);
	}
	
	/**
	 * Makes the controller rumble for X seconds.
	 */
	public void rumble(float l, float r, double seconds) {
		double timer = System.currentTimeMillis();
		while(System.currentTimeMillis() <= timer + (seconds * 1000)) {
			// TODO: Does this work?
			rumble(l, r);
		}
		
		rumble(0,0);
	}
	
	// TODO: I have no clue how this works.
	public enum Direction {
		UP(0),
		UP_RIGHT(45),
		RIGHT(90),
		DOWN_RIGHT(135),
		DOWN(180),
		DOWN_LEFT(225),
		LEFT(270),
		UP_LEFT(315),
		
		NONE(-1);
		
		public static Direction[] allDirections = new Direction[]{Direction.UP, Direction.UP_RIGHT, Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN, Direction.DOWN_LEFT, Direction.LEFT, Direction.UP_LEFT, Direction.NONE};
		public int angle;
		
		Direction(int angle) {
			this.angle = angle;
		}			
		
		public boolean isUp() {
			return (this == Direction.UP_LEFT || this == Direction.UP || this == Direction.UP_RIGHT);
		}
		
		public boolean isRight() {
			return (this == Direction.UP_RIGHT || this == Direction.RIGHT || this == Direction.DOWN_RIGHT);
		}
		
		public boolean isDown() {
			return (this == Direction.DOWN_LEFT || this == Direction.DOWN || this == Direction.DOWN_RIGHT);
		}
		
		public boolean isLeft() {
			return (this == Direction.UP_LEFT || this == Direction.LEFT || this == Direction.DOWN_LEFT);
		}
		
		public static Direction toDirection(int angle) {
			for (Direction d : allDirections) {
				if (d.angle == angle) {
					return d;
				}
			}
			return null;
		}
	}
}
