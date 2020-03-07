/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveSubsystem extends SubsystemBase {

  private CANSparkMax frontLeft = new CANSparkMax(DriveConstants.FRONT_LEFT, MotorType.kBrushless);
  private CANSparkMax backLeft = new CANSparkMax(DriveConstants.BACK_LEFT, MotorType.kBrushless);
  private CANSparkMax frontRight = new CANSparkMax(DriveConstants.FRONT_RIGHT, MotorType.kBrushless);
  private CANSparkMax backRight = new CANSparkMax(DriveConstants.BACK_RIGHT, MotorType.kBrushless);

  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(frontLeft, backLeft);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRight, backRight);

  private DifferentialDrive differentialDrive = new DifferentialDrive(leftMotors, rightMotors);

  private CANEncoder leftEncoder;
  private CANEncoder rightEncoder;

  private boolean isForward = true;
  private double maxSpeed = DriveConstants.NORMAL_SPEED;

  private final Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
  private final AnalogGyro yaw = new AnalogGyro(1);

  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {

    frontLeft.restoreFactoryDefaults();
    backLeft.restoreFactoryDefaults();
    frontRight.restoreFactoryDefaults();
    backRight.restoreFactoryDefaults();

    frontLeft.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    backLeft.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    frontRight.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    backRight.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

    frontLeft.setIdleMode(IdleMode.kBrake);
    backLeft.setIdleMode(IdleMode.kBrake);
    frontRight.setIdleMode(IdleMode.kBrake);
    backRight.setIdleMode(IdleMode.kBrake);

    frontLeft.setOpenLoopRampRate(.05);
    backLeft.setOpenLoopRampRate(.05);
    frontRight.setOpenLoopRampRate(.05);
    backRight.setOpenLoopRampRate(.05);

    leftEncoder = frontLeft.getEncoder();
    rightEncoder = frontRight.getEncoder();

    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);

    differentialDrive.setDeadband(0.1);

    yaw.calibrate();
    gyro.calibrate();
  }

  public void resetGyro() {
    gyro.reset();
    yaw.reset();
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public void turboOn() {
    maxSpeed = DriveConstants.TURBO_SPEED;
  }

  public void turboOff() {
    maxSpeed = DriveConstants.NORMAL_SPEED;
  }

  public void slowOn() {
    maxSpeed = DriveConstants.SLOW_SPEED;
  }

  public void slowOff() {
    maxSpeed = DriveConstants.NORMAL_SPEED;
  }

  private double adjustSpeed(double speed) {

    if (speed >= 0) {
      speed = Math.min(speed, maxSpeed);
    } else {
      speed = Math.max(speed, -maxSpeed);
    }

    return speed;
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    leftSpeed = adjustSpeed(leftSpeed);
    rightSpeed = adjustSpeed(rightSpeed);
    SmartDashboard.putNumber("Left Speed", leftSpeed);
    SmartDashboard.putNumber("Right Speed", rightSpeed);
    differentialDrive.feed();
    differentialDrive.feedWatchdog();
    if (isForward) {
      differentialDrive.tankDrive(leftSpeed, rightSpeed, false);
    } else {
      differentialDrive.tankDrive(rightSpeed * -1, leftSpeed * -1, false);
    }
  }

  public void resetDriveEncoders() {
    rightEncoder.setPosition(0);
    leftEncoder.setPosition(0);
  }

  public void stopDrive() {
    tankDrive(0, 0);
  }

  public double getDriveEncoderPosition() {
    return rightEncoder.getPosition();
  }

  public void invertTankDrive() {
    isForward = !isForward;
  }

  public void arcadeDrive(double xSpeed, double zRotation) {
    differentialDrive.arcadeDrive(xSpeed, zRotation);
  }

  @Override
  public void periodic() {
    Dashboard.putDebugNumber("Left Encoder Value", leftEncoder.getPosition());
    Dashboard.putDebugNumber("Right Encoder Value", rightEncoder.getPosition());
    Dashboard.putDebugNumber("Gyro Angle", gyro.getAngle());
  }
}
