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

    liftMotor.setIdleMode(IdleMode.kBrake);

    liftEncoder = liftMotor.getEncoder();
    liftEncoder.setPosition(0);
  }

  public void raiseLift() {
    liftMotor.set(HangConstants.LIFT_SPEED);
    Dashboard.putDebugNumber("Lift Encoder Position", liftEncoder.getPosition());
  }

  public void stopLift() {
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

  public double liftPosition() {
    return liftEncoder.getPosition();
  }

  public void lowerLift() {
    liftMotor.set(-HangConstants.LIFT_SPEED);
  }

  public void winchStart() {
    winchMotor.set(HangConstants.WINCH_MOTOR_SPEED);
  }

  public void winchStop() {
    winchMotor.stopMotor();
  }

  public void resetEncoder() {
    liftEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    Dashboard.putDebugNumber("Lift Encoder", liftEncoder.getPosition());
  }
}
