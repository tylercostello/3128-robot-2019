package org.team3128.testbench.main;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.util.units.Length;

import edu.wpi.first.wpilibj.Joystick;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.hardware.misc.Piston;
import org.team3128.common.hardware.misc.TwoSpeedGearshift;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.POVValue;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.listener.controltypes.Button;
import org.team3128.common.listener.controltypes.POV;
import org.team3128.common.narwhaldashboard.NarwhalDashboard;
import org.team3128.common.util.Constants;
import org.team3128.common.util.Log;
import org.team3128.common.util.enums.Direction;
import org.team3128.common.util.units.Angle;
import org.team3128.common.util.units.Length;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.util.units.Length;

import edu.wpi.first.wpilibj.Joystick;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.hardware.misc.Piston;
import org.team3128.common.hardware.misc.TwoSpeedGearshift;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.POVValue;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.listener.controltypes.Button;
import org.team3128.common.listener.controltypes.POV;
import org.team3128.common.narwhaldashboard.NarwhalDashboard;
import org.team3128.common.util.Constants;
import org.team3128.common.util.Log;
import org.team3128.common.util.enums.Direction;
import org.team3128.common.util.units.Angle;
import org.team3128.common.util.units.Length;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
public class Navx extends NarwhalRobot {
    AHRS ahrs;
    public TalonSRX boi1, boi2;
    public ListenerManager listenerLeft, listenerRight;
    public Joystick leftJoystick, rightJoystick;
    public double wheelCirc;
    public int lowGearMaxSpeed;
    public SRXTankDrive drive;
    public NetworkTable table;

	@Override
	protected void constructHardware()
	{
		table = NetworkTableInstance.getDefault().getTable("limelight");
        ahrs = new AHRS(SPI.Port.kMXP); 
        boi1 = new TalonSRX(1);
        boi2 = new TalonSRX(2);

        SRXTankDrive.initialize(boi1, boi2, wheelCirc, 1, 25.25 * Length.in, 30.5 * Length.in,
				lowGearMaxSpeed);
        drive = SRXTankDrive.getInstance();
        
        leftJoystick = new Joystick(0);
        rightJoystick = new Joystick(1);

        listenerLeft = new ListenerManager(leftJoystick);
        listenerRight = new ListenerManager(rightJoystick);

        addListenerManager(listenerLeft);
        addListenerManager(listenerRight);

    }

    @Override
    protected void setupListeners() {

        listenerRight.nameControl(ControllerExtreme3D.JOYY, "MoveForwards");
		listenerRight.nameControl(ControllerExtreme3D.TWIST, "MoveTurn");
		listenerRight.nameControl(ControllerExtreme3D.THROTTLE, "Throttle");

		listenerRight.addMultiListener(() ->
		{
			double x = listenerRight.getAxis("MoveForwards");
			double y = listenerRight.getAxis("MoveTurn");
			double t = listenerRight.getAxis("Throttle") * -1;
			drive.arcadeDrive(x, y, t, true);
        }, "MoveForwards", "MoveTurn", "Throttle");
        
        listenerRight.nameControl(new Button(2), "LightOn");
		listenerRight.addButtonDownListener("LightOn", () -> {
		    table.getEntry("ledMode").setNumber(3);
        });
        listenerRight.nameControl(new Button(2), "LightOff");
		listenerRight.addButtonUpListener("LightOff", () -> {
		    table.getEntry("ledMode").setNumber(1);
		});
		listenerRight.nameControl(ControllerExtreme3D.TRIGGER, "LightBlink");
		listenerRight.addButtonDownListener("LightBlink", () -> { 
			table.getEntry("ledMode").setNumber(2);
		});
    }

    @Override
    protected void constructAutoPrograms() {

    }

    @Override
    protected void teleopInit() {

    }

    @Override
    protected void teleopPeriodic() {
    Log.debug("Debug", "####################################");
   // Log.debug("Yaw:", Float.toString(ahrs.getYaw()));
    Log.debug("Pitch", Float.toString(ahrs.getPitch()));
    Log.debug("Roll:", Float.toString(ahrs.getRoll()));
    //Log.debug("AccelX:",Float.toString(ahrs.getWorldLinearAccelX()));
    //Log.debug("AccelY:",Float.toString(ahrs.getWorldLinearAccelY()));
    //boi1.set(ControlMode.PercentOutput,((int)(ahrs.getYaw())/10));
    
    }

    @Override
    protected void autonomousInit() {
        
    }

    public static void main(String[] args) {
        RobotBase.startRobot(Navx::new);
    }

}