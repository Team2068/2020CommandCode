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
import frc.robot.Constants.LowScoringConstants;

public class LowScoringSubsystem extends SubsystemBase {

  private CANSparkMax conveyorMotor = new CANSparkMax(LowScoringConstants.CONVEYOR_MOTOR, MotorType.kBrushless);

  private CANSparkMax intakeMotor = new CANSparkMax(LowScoringConstants.INTAKE_MOTOR, MotorType.kBrushless);

  /**
   * Creates a new IntakeSubsystem.
   */
  public LowScoringSubsystem() {

    conveyorMotor.restoreFactoryDefaults();
    intakeMotor.restoreFactoryDefaults();

    conveyorMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    intakeMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

  }

  public void intakeControl() {

    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
