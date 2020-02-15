/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

  private boolean isForward = true;
  private boolean isTurbo = false;
  private boolean isSlow = false;
  private boolean steppingSpeedUp = false;
  private boolean steppingSpeedDown = false;
  private double steppingSpeed = .1;

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

  public void steppingSpeedUp(){
    steppingSpeedUp = true;
  }
  public void steppingSpeedDown() {
    steppingSpeedDown = true;
  }

  private double adjustSpeed(double speed) {
    if (isTurbo) {
      if (speed >= 0) {
        speed = 1;
      } else {
        speed = -1;
      }
    }
    else if (isSlow) {
      if (speed >= 0) {
        speed = .25;
      }
      else {
        speed = -.25;
      }
    }
    else {
      if (speed >= .5) {
        speed = .5;
      }
      else if (speed <= .5) {
        speed = -.5;
      }
    }

    return speed;
  }

  private double steppingSpeed(double speed) {
    if (steppingSpeedUp) {
      if (speed >= 0 && speed < 1) {
        speed = speed + steppingSpeed;
      }
      else if (speed <= 0 && speed > 1) {
        speed = speed - steppingSpeed;
      }
    }
    else if (steppingSpeedDown) {
      if (speed > .2 && speed <= 1){
        
      }
    }

    return speed;
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    leftSpeed = adjustSpeed(leftSpeed) + steppingSpeed;
    rightSpeed = adjustSpeed(rightSpeed);

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
    // This method will be called once per scheduler run
  }
}
