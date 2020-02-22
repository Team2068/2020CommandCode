/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.HangConstants;

public class HangSubsystem extends SubsystemBase {

  private CANSparkMax liftMotor = new CANSparkMax(HangConstants.LIFT_MOTOR, MotorType.kBrushless);
  private CANSparkMax winchMotor = new CANSparkMax(HangConstants.WINCH_MOTOR, MotorType.kBrushless);
  private CANEncoder liftEncoder;

  /**
   * Creates a new LiftSubsystem.
   */
  public HangSubsystem() {
    setName("Hang Subsystem");
    liftMotor.restoreFactoryDefaults();
    winchMotor.restoreFactoryDefaults();

    liftMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    winchMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

    liftEncoder = liftMotor.getEncoder();
    liftEncoder.setPosition(0);
  }

  public void liftLift() {
    liftMotor.set(HangConstants.LIFT_SPEED);
    SmartDashboard.putNumber("Lift Encoder Position", liftEncoder.getPosition());
  }

  public void stopLifting() {
    liftMotor.stopMotor();
  }

  public void liftToHeight() {
    // going to have to encoder value this? Limit switch? Not sure what the plan is
    // will probably have to be a command
    if (liftEncoder.getPosition() < HangConstants.LIFT_ENCODER_VALUE) {
      liftMotor.set(HangConstants.LIFT_SPEED);
    } else {
      liftMotor.set(0);
    }
  }

  public void winchAndLowerLift() {
    // liftMotor.set(-0.42);
    // winchMotor.set(1);
    if (Math.abs(liftEncoder.getPosition()) > 1) {
      liftMotor.set(-HangConstants.LIFT_SPEED);
      winchMotor.set(1);
    } else {
      liftMotor.stopMotor();
      winchMotor.stopMotor();
    }
  }

  public void resetEncoder() {
    liftEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Encoder Value", liftEncoder.getPosition());
    // This method will be called once per scheduler run
  }
}
