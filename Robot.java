package org.usfirst.frc.team6489.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 **/
public class Robot extends IterativeRobot {
	// Used in teleOp
	public Boolean toggle = false;
	public Boolean previousToggleButton = false;
	public Boolean currentToggleButton = false;
	
	public Boolean open = false;
	public Boolean close = false;
	
	/** Negative is forwards! **/
	Spark rightWheels;
	/** Positive is forwards! **/
	Spark leftWheels;
	
	/** ? is up! **/
	Spark forklift;
	/** ? is out! **/
	Spark cubeMotor;
	
	public DoubleSolenoid arm = new DoubleSolenoid(5, 4);
	public Compressor comp = new Compressor();

	public static OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 **/
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		
		comp.start();
		
		// Wheels
		rightWheels = new Spark(4);
		leftWheels = new Spark(1);
		
		// Forklift (check port)
		forklift = new Spark(2);
		
		// Cube input/output (check port)
		cubeMotor = new Spark(0);
		
		oi = new OI();
	}

	@Override
	public void autonomousInit() {
		comp.start(); // Should start compressor in auto too
	}

	/**
	 * This function is called periodically during autonomous.
	 **/
	@Override
	public void autonomousPeriodic() {
		// until loop?
		rightWheels.set(-0.24);
		leftWheels.set(0.24);
	}

	/**
	 * This function is called periodically during operator control.
	 **/
	@Override
	public void teleopPeriodic() {
		// Driving (tank style again)
		
		previousToggleButton = currentToggleButton;
		currentToggleButton = OI.xbox.getRawButton(3); // X

		if (currentToggleButton && !previousToggleButton) {
			toggle = toggle ? false : true;
		}
		
		// Opens or closes one pneumatic side of our robot's arms
		open = OI.xbox.getRawButton(4);  // Y
		close = OI.xbox.getRawButton(2); // B
		
		if (open) {
			arm.set(DoubleSolenoid.Value.kForward);
		} else if (close) {
			arm.set(DoubleSolenoid.Value.kReverse);
		} else {
			arm.set(DoubleSolenoid.Value.kOff);
		}
		
		// Have to be reversed because something is sketchy
		rightWheels.set((double)(toggle ? OI.xbox.getY2() / 2 : OI.xbox.getY2()));
		leftWheels.set((double)(toggle ? -OI.xbox.getY1() / 2 : -OI.xbox.getY1()));
		
		// Cube intake/output (uses left bumper for out and right bumper for in) - maybe idk
		if (OI.xbox.getRawButton(5)) { // Sucks in
			cubeMotor.set(0.5);
		} else if (OI.xbox.getRawButton(6)) { // Goes out
			cubeMotor.set(-0.5);
		} else {
			cubeMotor.set(0);
		}
		
		double forkliftUp = OI.xbox.getLeftTrigger();
		double forkliftDown = OI.xbox.getRightTrigger();
		
		// Forklift (uses left trigger for in and right trigger for out)
		if (forkliftUp == 0 && forkliftDown == 0) {
			forklift.set(0);
		} else if (forkliftUp != 0) {  // Goes up!
			forklift.set(0.75);
		} else if (forkliftDown != 0) { // Goes down!
			forklift.set(-0.75);
		}
	}
}

