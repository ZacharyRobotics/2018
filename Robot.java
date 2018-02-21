package org.usfirst.frc.team6489.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
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
	
	public Timer timer = new Timer();
	
	
	/** Is this even used? **/
	public static Robot self;
	
	/** Gets the team's color from the Driver Station. **/
	public static Alliance allianceColor = DriverStation.getInstance().getAlliance();
	
	/** The starting location of the robot; used in autonomous. **/
	public static int location = DriverStation.getInstance().getLocation();
	
	/** Easy way to measure out commands in autonomous; records start time of auto. **/
	public double start = 0;
	/** Contains the locations of the team point-earners (switch and scale) **/
	public String gameData;
	
	// Used in teleOp
	public Boolean toggle = false;
	public Boolean previousButton = false;
	public Boolean currentButton = false;
	public long forkliftStopper = 0L;
	
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
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		start = System.currentTimeMillis();
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 **/
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		double time = System.currentTimeMillis();
		
		switch (location) {
			case 1:
				if (gameData.charAt(0) == 'L') { // We are left and switch is left
					// TODO: Test timing!
					// Goes straight to the switch and puts cube in
					if (time <= start + 1300) {
						rightWheels.set(-0.75); // Goes forward (1.3 seconds)
						leftWheels.set(0.75);
					} else if (time <= start + 1700) {
						forklift.set(0.4); // Forklift rises (0.4 seconds)
					} else if (time <= start + 2200) {
						cubeMotor.set(0.35); // Cube shoots out (0.5 seconds)
					}
				} else if (gameData.charAt(0) == 'R') { // We are left and switch is right
					// TODO: Test timing!
					// Goes the long way around the switch (avoid collisions)
					if (time <= start + 1300) {
						rightWheels.set(-0.75); // Goes forward (1.3 seconds)
						leftWheels.set(0.75);
					} else if (time <= start + 1600) {
						rightWheels.set(0.5); // Turns left (0.3 seconds)
						leftWheels.set(0.5);
					} else if (time <= start + 2300) {
						rightWheels.set(-0.5); // Goes forward (0.7 seconds)
						leftWheels.set(0.5);
					} else if (time <= start + 2600) {
						rightWheels.set(0.5); // Turns right (0.3  seconds)
						leftWheels.set(0.5);
					} else if (time <= start + 3100) {
						rightWheels.set(-0.75); // Goes forward (0.5 seconds)
						leftWheels.set(0.75);
					} else if (time <= start + 3400) {
						rightWheels.set(0.5); // Turns right (0.3 seconds)
						leftWheels.set(0.5);
					} else if (time <= start + 4300) {
						rightWheels.set(-0.75); // Goes forward (0.9 seconds)
						leftWheels.set(0.75);
					} else if (time <= start + 4600) {
						rightWheels.set(0.5); // Turns right (0.3 seconds)
						leftWheels.set(0.5);
					} else if (time <= start + 5350) {
						forklift.set(0.3); // Forklift rises (0.75 seconds)
					} else if (time <= start + 5850) {
						cubeMotor.set(0.35); // Cube dispenses (0.5 seconds)
					}
				}
				
				break;
			case 2:
				if (time <= start + 3000) {
					rightWheels.set(-0.75);
					leftWheels.set(0.75);
				} else if (time <= start + 3275) {
					rightWheels.set(-0.25);
					leftWheels.set(0.25);
				} else if (time <= start + 3350) {
					// Input
				} else if (time <= start + 3500 ) { // Turns left for 
					rightWheels.set(-0.25);
					leftWheels.set(-0.25);
				}
				
				break;
			case 3:
				if (timer.get() < 1.0) {
					rightWheels.set(-0.75);
					leftWheels.set(0.75);
				}/*else if (timer.get() < 0.8) {
					rightWheels.set(0);
					leftWheels.set(0);
				} else if (timer.get() < 1.6) {
					rightWheels.set(-0.75);
					leftWheels.set(0.75);
				} else if (timer.get() < 2.2) {
					rightWheels.set(0);
					leftWheels.set(0);
					
					forklift.set(0.6);
				} else if (timer.get() < 2.7) {
					forklift.set(0);
					cubeMotor.set(-0.5);
				}*/ else {
					//cubeMotor.set(0);
					rightWheels.set(0);
					leftWheels.set(0);
					
					timer.stop();
				}
				
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
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
		double speed = OI.xbox.getY1();
		double right = speed + (OI.xbox.getX2() / 2);
		double left = speed - (OI.xbox.getX2() / 2);
		rightWheels.set((double)(toggle ? right / 2 : right));
		leftWheels.set((double)(toggle ? -left / 2 : -left));
		
		// Forklift (uses left bumper for up and right bumper for down)
		if (OI.xbox.getRawButton(5)) { // Goes up!
			forklift.set(0.75);
			//forkliftStopper++;
		} else if (OI.xbox.getRawButton(6)) { // Goes down!
			// Gives it a buffer of 3 in case there is a hardware mistake
			//if (forkliftStopper >= 0) {
				forklift.set(-0.75);
				//forkliftStopper--;
			//} else {
			//	forklift.set(0);
			//}
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

