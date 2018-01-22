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
	final String leftAuto = "Left Starting Position";
	final String centerAuto = "Center Starting Position";
	final String rightAuto = "Right Starting Position";
	String autonomousCommand;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	public static Robot self;
	public static Alliance allianceColor = DriverStation.getInstance().getAlliance();
	public double start = 0;
	
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
		System.out.println("Our alliance color is " + allianceColor);
		
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
		//autonomousCommand = chooser.getSelected();
		autonomousCommand = SmartDashboard.getString("Autonomous Selector", centerAuto);
		System.out.println("Autonomous selected: " + autonomousCommand);
		System.out.println("Our alliance color is " + allianceColor);
	}

	/**
	 * This function is called periodically during autonomous
	 **/
	@Override
	public void autonomousPeriodic() {
		System.out.println("Our alliance color is " + allianceColor);
		Scheduler.getInstance().run(); // Is this necessary? TODO: If it doesn't work, try commenting this out!
		switch (autonomousCommand) {
			case leftAuto:
				// Go straight
				//String light = "Blue";//sensor.getColor(); (modify to take input from light sensor)
				if (/*light == */allianceColor != null) {
					// Implement a while loop like the default, but FIXED (loop can end)
					forklift.set(0.3);
					cubeMotor.set(0.35);
				} else {
					// Same type of loop as above (timer as a general time but if statements for individual instructions? Cleaner code!)
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
			case centerAuto:
			default:
				System.out.println("Our alliance color is " + allianceColor);
				
				if (start == 0) {
					start = System.currentTimeMillis();
				}
				
				double time = System.currentTimeMillis();
				
				// TODO: Modify condition so it will switch from auto to teleOp (set public variable to System Time?)
				if (time <= start + 1000) {
					rightWheels.set(-0.25);
					leftWheels.set(0.25);
				} else if (time > start) {
					rightWheels.set(0);
					leftWheels.set(0);
				}
				
				break;
			case rightAuto:
				
				break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 **/
	@Override
	public void teleopPeriodic() {
		// Driving (uses left joystick for left wheels and right joystick for right wheels)
		leftWheels.set(-OI.xbox.getY1() / 3); // TODO: Toggle between divisors with X button
		rightWheels.set(OI.xbox.getY2() / 3);
		
		// Forklift (uses left bumper for up and right bumper for down)
		if (OI.forkliftUp != null) {
			forklift.set(0.5);
		} else if (OI.forkliftDown != null) {
			forklift.set(-0.5);
		}
		
		double cubeIn = OI.xbox.getLeftTrigger();
		double cubeOut = OI.xbox.getRightTrigger();
		
		// Cube intake/output (uses left trigger for in and right trigger for out)
		if (cubeIn != 0) {
			// TODO: Check what numbers are output!
			cubeMotor.set(cubeIn / 2);
		}
		
		if (cubeOut != 0) {
			cubeMotor.set(-cubeOut / 2);
		}
	}
}

