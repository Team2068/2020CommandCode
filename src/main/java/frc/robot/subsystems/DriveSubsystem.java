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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

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
  private boolean isTurbo = false;
  private boolean isSlow = false;

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

    leftEncoder = frontLeft.getEncoder();
    rightEncoder = frontRight.getEncoder();

    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);

    differentialDrive.setDeadband(0.1);
  }

  public void turboOn() {
    isTurbo = true;
  }

  public void turboOff() {
    isTurbo = false;
  }

  public void slowOn() {
    isSlow = true;
  }

  public void slowOff() {
    isSlow = false;
  }

  private double adjustSpeed(double speed) {
    SmartDashboard.putString("Is Turbo", isTurbo ? "yes" : "no");
    SmartDashboard.putString("Is Slow", isSlow ? "yes" : "no");
    if (isTurbo) {
      if (speed >= 0) {
        speed = 1;
      } else {
        speed = -1;
      }
    } else if (isSlow) {
      if (speed >= 0) {
        speed = .25;
      } else {
        speed = -.25;
      }
    } else {
      if (speed >= 0) {
        speed = Math.min(speed, 0.8);
      } else {
        speed = Math.max(speed, -.8);
      }
    }

    return speed;
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    leftSpeed = adjustSpeed(leftSpeed);
    rightSpeed = adjustSpeed(rightSpeed);
    SmartDashboard.putNumber("Left Speed", leftSpeed);
    SmartDashboard.putNumber("Right Speed", rightSpeed);
    if (isForward) {
      differentialDrive.tankDrive(leftSpeed, rightSpeed);
    } else {
      differentialDrive.tankDrive(rightSpeed * -1, leftSpeed * -1);
    }
  }

  public void invertTankDrive() {
    isForward = !isForward;
  }

  public void arcadeDrive(double xSpeed, double zRotation) {
    differentialDrive.arcadeDrive(xSpeed, zRotation);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Encoder Value", leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Encoder Value", rightEncoder.getPosition());
  }
}
