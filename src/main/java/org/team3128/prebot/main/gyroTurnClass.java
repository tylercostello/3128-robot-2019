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

public class gyroTurnClass extends GoodGyroTurn{
//AHRS ahrs;
public static void gyroTurn(TalonSRX leftDriveFront,TalonSRX rightDriveFront, double goal) {
    AHRS ahrs;
    ahrs = new AHRS(SPI.Port.kMXP); 
    ahrs.reset();
    Double yaw=ahrs.getAngle();
    Float pitchThreshold = (float)10;     
    Float pitch=ahrs.getPitch();
    while (yaw<goal){

        yaw=ahrs.getAngle();

        if (yaw<goal){
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