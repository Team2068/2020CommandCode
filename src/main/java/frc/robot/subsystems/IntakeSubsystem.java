/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

  private CANSparkMax rampMotor = new CANSparkMax(IntakeConstants.RAMP_MOTOR, MotorType.kBrushless);

  private CANSparkMax intakeMotor = new CANSparkMax(IntakeConstants.INTAKE_MOTOR, MotorType.kBrushless);
  /**
   * Creates a new IntakeSubsystem.
   */
  public IntakeSubsystem() {

    rampMotor.restoreFactoryDefaults();
    intakeMotor.restoreFactoryDefaults();

    rampMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    intakeMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
