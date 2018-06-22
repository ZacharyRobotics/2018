package org.usfirst.frc.team6489.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 **/
public class Robot extends IterativeRobot {
	SendableChooser<String> chooser = new SendableChooser<>();
	
	/** Is this even used? **/
	public static Robot self;
	
	// Used in teleOp
	public Boolean toggle = false;
	public Boolean previousButton = false;
	public Boolean currentButton = false;
	
	/** Negative is forwards! **/
	Spark rightWheels;
	/** Positive is forwards! **/
	Spark leftWheels;
	
	/** ? is up! **/
	Spark forklift;
	/** ? is out! **/
	Spark cubeMotor;

	public static OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 **/
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		
		// Wheels
		rightWheels = new Spark(4);
		leftWheels = new Spark(1);
		
		// Forklift (check port)
		forklift = new Spark(2);
		
		// Cube input/output (check port)
		cubeMotor = new Spark(0);
		
		oi = new OI();
		self = this;
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro.
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 **/
	@Override
	public void autonomousInit() {

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
		// Driving (no longer tank drive; uses left for front/back and right for direction)
		
		previousButton = currentButton;
		currentButton = OI.xbox.getRawButton(3);

		if (currentButton && !previousButton) {
			toggle = toggle ? false : true;
		}
		
		// Have to be reversed because something is sketchy
		double speed = OI.xbox.getY1();
		double right = speed + (OI.xbox.getX2() / 2);
		double left = speed - (OI.xbox.getX2() / 2);
		rightWheels.set((double)(toggle ? right / 2 : right / 1.25));
		leftWheels.set((double)(toggle ? -left / 2 : -left / 1.25));
		
		// Forklift (uses left bumper for up and right bumper for down)
		if (OI.xbox.getRawButton(5)) { // Goes up!
			forklift.set(0.75);
		} else if (OI.xbox.getRawButton(6)) { // Goes down!
			forklift.set(-0.75);
		} else {
			forklift.set(0);
		}
		
		double cubeIn = OI.xbox.getLeftTrigger();
		double cubeOut = OI.xbox.getRightTrigger();
		
		// Cube intake/output (uses left trigger for in and right trigger for out)
		if (cubeIn == 0 && cubeOut == 0) {
			cubeMotor.set(0);
		} else if (cubeIn != 0) {
			cubeMotor.set(cubeIn / 2);
		} else if (cubeOut != 0) {
			cubeMotor.set(-cubeOut / 2);
		}
	}
}

