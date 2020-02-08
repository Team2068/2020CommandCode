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

  public static CANSparkMax conveyorMotor = new CANSparkMax(LowScoringConstants.CONVEYOR_MOTOR, MotorType.kBrushless);

  public static CANSparkMax intakeMotor = new CANSparkMax(LowScoringConstants.INTAKE_MOTOR, MotorType.kBrushless);

  private int intakeDirection = -1;
  /**
   * Creates a new IntakeSubsystem.
   */
  public LowScoringSubsystem() {

    conveyorMotor.restoreFactoryDefaults();
    intakeMotor.restoreFactoryDefaults();

    conveyorMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    intakeMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

  }

  public static void conveyorIn(){
    conveyorMotor.set(LowScoringConstants.CONVEYOR_SPEED);
  }
  public static void conveyorOut(){
    conveyorMotor.set(LowScoringConstants.CONVEYOR_SPEED * -1);
  }
  public void conveyorStop(){
    conveyorMotor.set(0);
  }

  //probably just need a command, "intake on"
  public static void intakeIn(){
    intakeMotor.set(LowScoringConstants.INTAKE_SPEED);
  } 
  public static void intakeOut(){
    intakeMotor.set(LowScoringConstants.INTAKE_SPEED);
  }
  public static void intakeOff(){
    intakeMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
