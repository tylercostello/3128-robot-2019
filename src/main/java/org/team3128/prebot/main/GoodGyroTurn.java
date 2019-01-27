package org.team3128.prebot.main;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.team3128.common.NarwhalRobot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.util.Constants;
import org.team3128.common.util.units.Angle;
import org.team3128.common.util.units.Length;
import org.team3128.common.util.Log;
import org.team3128.common.util.RobotMath;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.POVValue;
import org.team3128.common.listener.controltypes.POV;
import org.team3128.common.narwhaldashboard.NarwhalDashboard;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.listener.controltypes.Button;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class GoodGyroTurn extends NarwhalRobot {
    AHRS ahrs;

    public TalonSRX rightDriveFront;
    public TalonSRX rightDriveMiddle;
    public TalonSRX rightDriveBack;
    public TalonSRX leftDriveFront;
    public TalonSRX leftDriveMiddle;
    public TalonSRX leftDriveBack;

    public SRXTankDrive tankDrive;

    public Joystick joystick;

    public ListenerManager lm;

    public ADXRS450_Gyro gyro;
    public double wheelDiameter;
    public int counter=0;
    public double maxLeftSpeed = 0;
    public double maxRightSpeed = 0;
    public NetworkTable table;
    public NetworkTable table2;

    public double valCurrent1 = 0.0;
    public double valCurrent2 = 0.0;
    public double valCurrent3 = 0.0;
    public double valCurrent4 = 0.0;

    //public CommandGroup cmdRunner;

	@Override
	protected void constructHardware()
	{
        
        ahrs = new AHRS(SPI.Port.kMXP); 
        ahrs.reset();
		table = NetworkTableInstance.getDefault().getTable("limelight");

        rightDriveFront = new TalonSRX(0);
        rightDriveMiddle = new TalonSRX(1);
        rightDriveBack = new TalonSRX(2);

        leftDriveFront = new TalonSRX(3);
        leftDriveMiddle = new TalonSRX(4);
        leftDriveBack = new TalonSRX(5);

        rightDriveFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
        leftDriveFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
        
        rightDriveMiddle.set(ControlMode.Follower, rightDriveFront.getDeviceID());
        leftDriveMiddle.set(ControlMode.Follower, leftDriveFront.getDeviceID());
        rightDriveBack.set(ControlMode.Follower, rightDriveFront.getDeviceID());
        leftDriveBack.set(ControlMode.Follower, leftDriveFront.getDeviceID());

        wheelDiameter = 3.68 * Length.in;
        SRXTankDrive.initialize(rightDriveFront, leftDriveFront, wheelDiameter * Math.PI, 1, 23.70*Length.in, 400);
        tankDrive = SRXTankDrive.getInstance();
        //tankDrive.setRightSpeedScalar(0.1);//0.96038845);
        
        //rightDriveFront.setInverted(true);
        //rightDriveMiddle.setInverted(true);
        //rightDriveBack.setInverted(true);

        leftDriveFront.setInverted(true);
        leftDriveMiddle.setInverted(true);
        leftDriveBack.setInverted(true);

        leftDriveFront.setSensorPhase(true);
        rightDriveFront.setSensorPhase(true);
        
        joystick = new Joystick(1);
		lm = new ListenerManager(joystick);
        addListenerManager(lm);
        
        gyro = new ADXRS450_Gyro();
		gyro.calibrate();
    }
    
  

	@Override
	protected void setupListeners() {
      
    }
    
    @Override
    protected void updateDashboard() {
        //NarwhalDashboard.put("tx", table.getEntry("tx").getNumber(0));
        //hey
        NarwhalDashboard.put("tx", table.getEntry("tx").getDouble(0.0));
        NarwhalDashboard.put("ty", table.getEntry("ty").getDouble(0.0));
        NarwhalDashboard.put("tv", table.getEntry("tv").getDouble(0.0));
        NarwhalDashboard.put("ta", table.getEntry("ta").getDouble(0.0));
        NarwhalDashboard.put("ts", table.getEntry("ts").getDouble(0.0));
        NarwhalDashboard.put("tl", table.getEntry("tl").getDouble(0.0));
        SmartDashboard.putNumber("Gyro Angle", RobotMath.normalizeAngle(gyro.getAngle()));

		SmartDashboard.putNumber("Left Speed (nu/100ms)", leftDriveFront.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Right Speed (nu/100ms)", rightDriveFront.getSelectedSensorVelocity(0));
        
        maxLeftSpeed = Math.max(leftDriveFront.getSelectedSensorVelocity(), maxLeftSpeed);
        maxRightSpeed = Math.max(rightDriveFront.getSelectedSensorVelocity(), maxRightSpeed);

        SmartDashboard.putNumber("Max Left Speed", maxLeftSpeed);
        SmartDashboard.putNumber("Max Right Speed", maxRightSpeed);
		
    }
    public static void main(String... args) {
        RobotBase.startRobot(GoodGyroTurn::new);
        //ahrs.reset();
        //hey
    }

   
    @Override
    protected void teleopInit() {
        ahrs.reset();
        //gyroTurnClass.gyroTurn(leftDriveFront,rightDriveFront,359.9);
        
    }

    @Override
    protected void teleopPeriodic() {
  
        Double yaw=ahrs.getAngle();
        Float pitchThreshold = (float)10;     
        Float pitch=ahrs.getPitch();
        while (yaw<359.9){

            yaw=ahrs.getAngle();

            if (yaw<359.9){
        	    leftDriveFront.set(ControlMode.PercentOutput,-(0.1));
      		    rightDriveFront.set(ControlMode.PercentOutput,(0.1));
            }
            else{
                Log.debug("Yaw", Double.toString(yaw));
            }
          
        }
        leftDriveFront.set(ControlMode.PercentOutput,(0));
      	rightDriveFront.set(ControlMode.PercentOutput,(0));
   
    }

}