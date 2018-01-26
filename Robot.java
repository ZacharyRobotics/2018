package org.usfirst.frc.team6489.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	/** Gets the team's color from the Driver Station. **/
	public static Alliance allianceColor = DriverStation.getInstance().getAlliance();
	/** Holds the team color converted to a string value; used for light sensor comparison. **/
	public String color;
	public String light;
	
	/** The starting location of the robot; used in autonomous. **/
	public static int station = DriverStation.getInstance().getLocation();
	
	/** Easy way to measure out commands in autonomous; records start time of auto. **/
	public double start = 0;
	
	// Used in teleOp
	public Boolean toggle = false;
	public Boolean previousButton = false;
	public Boolean currentButton = false;
	
	/** Negative is forwards! **/
	Spark rightWheels;
	/** Positive is forwards! **/
	Spark leftWheels;
	
	Spark forklift;
	Spark cubeMotor;

	public static OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 **/
	@Override
	public void robotInit() {
		// Easy way to compare with the light sensor output
		if (allianceColor == DriverStation.Alliance.Blue) {
			color = "Blue";
		} else {
			color="Red";
		}
		
		// Wheels
		rightWheels = new Spark(0);
		leftWheels = new Spark(1);
		
		// Forklift (check port)
		forklift = new Spark(2);
		
		// Cube input/output (check port)
		cubeMotor = new Spark(3);
		
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
	 * This function is called periodically during autonomous
	 **/
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		switch (station) {
			case 1:
				// Go straight
				light = "Blue"; // TODO: FIX!
				
				if (light == color) {
					// Implement a while loop like the default, but FIXED (loop can end)
					forklift.set(0.3);
					cubeMotor.set(0.35);
				} else {
					rightWheels.set(-0.5); // Turns left
					leftWheels.set(-0.5);
					
					// For if we are going to go around the long way
					rightWheels.set(-0.5); // Goes forward
					leftWheels.set(0.5);
					
					rightWheels.set(0.5); // Turns right
					leftWheels.set(0.5);
					
					rightWheels.set(-0.5); // Goes forward
					leftWheels.set(0.5);
					
					rightWheels.set(0.5); // Turns right
					leftWheels.set(0.5);
					
					rightWheels.set(-0.5); // Goes forward
					leftWheels.set(0.5);
					
					rightWheels.set(0.5); // Turns right
					leftWheels.set(0.5);
					
					forklift.set(0.3); // Dispenses cube
					cubeMotor.set(0.35);
				}
				break;
			case 2:
			default:
				if (start == 0) {
					start = System.currentTimeMillis();
				}
				
				double time = System.currentTimeMillis();
				if (time <= start + 3000) {
					rightWheels.set(-0.75);
					leftWheels.set(0.75);
				} else if (time <= start + 3225) {
					rightWheels.set(-0.25);
					leftWheels.set(0.25);
				} else if (time <= start + 3250) { // TODO: Use this to make another switch case?
					// Input
				} else if (time <= start + 3500 ) { // Turns left
					rightWheels.set(-0.25);
					leftWheels.set(-0.25);
				}
				
				break;
			case 3:
				
				
				break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 **/
	@Override
	public void teleopPeriodic() {
		// Driving (uses left joystick for left wheels and right joystick for right wheels)
		
		previousButton = currentButton; 
		currentButton = OI.xbox.getRawButton(3);

		if (currentButton && !previousButton) {
			toggle = toggle ? false : true;
		}
		
		// Have to be reversed because something is sketchy
		rightWheels.set((double)(toggle ? OI.xbox.getY2() / 2 : OI.xbox.getY2()));
		leftWheels.set((double)(toggle ? -OI.xbox.getY1() / 2 : -OI.xbox.getY1()));
		
		// Forklift (uses left bumper for up and right bumper for down)
		if (OI.forkliftUp != null) {
			forklift.set(0.5);
		} else if (OI.forkliftDown != null) {
			forklift.set(-0.5);
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

