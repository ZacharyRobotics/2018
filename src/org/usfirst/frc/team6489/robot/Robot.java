package org.usfirst.frc.team6489.robot;

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
	// TODO: Multithreading? Check https://wpilib.screenstepslive.com/s/currentCS/m/java/l/681690-multithreading-in-java
	final String leftAuto = "Left Starting Position";
	final String centerAuto = "Center Starting Position";
	final String rightAuto = "Right Starting Position";
	String autonomousCommand;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	public static Robot self;
	public static String allianceColor = "Blue";
	
	/** Negative is forwards! **/
	Spark rightWheels;
	/** Positive is forwards! **/
	Spark leftWheels;
	
	Spark forklift;
	Spark cubeMotor;
	
	/* The controls need to be properly initialized. Check the below page if it doesn't work:
	 * https://github.com/FRC-1902/2015-game/blob/master/2015%20Robot/src/com/explodingbacon/robot
	 */
	public static OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 **/
	@Override
	public void robotInit() {
		chooser.addObject("Left Start", leftAuto);
		chooser.addObject("Center Start", centerAuto);
		chooser.addObject("Right Start", rightAuto);
		SmartDashboard.putData("Autonomous mode chooser", chooser);
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
		// TODO: Test if selecting different autonomous modes works!
		autonomousCommand = SmartDashboard.getString("Autonomous Selector", centerAuto);
		System.out.println("Autonomous selected: " + autonomousCommand);
	}

	/**
	 * This function is called periodically during autonomous
	 **/
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run(); // Is this necessary? TODO: If it doesn't work, try commenting this out!
		switch (autonomousCommand) {
			case leftAuto:
				// Go straight
				String light = "Blue";//sensor.getColor(); (modify to take input from light sensor)
				if (light == allianceColor) {
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
				// Put center auto code here
				double time = System.currentTimeMillis();
				while (true) {
					// TODO: Modify condition so it will switch from auto to teleOp (set public variable to System Time?)
					if (System.currentTimeMillis() <= time + 5000) {
						rightWheels.set(-0.5);
						leftWheels.set(0.5);
					} else {
						rightWheels.set(0);
						leftWheels.set(0);
					}
				}
				
				//break;
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
		leftWheels.set(-OI.xbox.getY1());
		rightWheels.set(OI.xbox.getY2());
		
		// Forklift (uses left bumper for up and right bumper for down)
		if (OI.forkliftUp != null) {
			// TODO: Test if it goes the right way.
			forklift.set(0.5);
		} else if (OI.forkliftDown != null) {
			forklift.set(-0.5);
		}
		
		double cubeIn = OI.xbox.getLeftTrigger();
		double cubeOut = OI.xbox.getRightTrigger();
		
		// Cube intake/output (uses left trigger for in and right trigger for out)
		if (cubeIn != 0) {
			// TODO: Test if it goes the right way.
			// TODO: Check what numbers are output!
			cubeMotor.set(cubeIn / 2);
		}
		
		if (cubeOut != 0) {
			cubeMotor.set(-cubeOut / 2);
		}
	}
}

