/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.LowScoringConstants;

public class LowScoringSubsystem extends SubsystemBase {

  private CANSparkMax conveyorMotor = new CANSparkMax(LowScoringConstants.CONVEYOR_MOTOR, MotorType.kBrushless);
  private CANSparkMax rollerMotor = new CANSparkMax(LowScoringConstants.ROLLER_MOTOR, MotorType.kBrushed);
  private CANEncoder rollerEncoder;
  private CANEncoder conveyorEncoder;

  private boolean rollersRunning = false;
  private int rollerDirection = 1;
  private double defaultConveyorStep = 5.0;

  private ShuffleboardTab tab = Shuffleboard.getTab("Low Scoring Subsystem");
  private NetworkTableEntry conveyorStep = tab.add("Conveyor Step", defaultConveyorStep).getEntry();

  public LowScoringSubsystem() {

    conveyorMotor.restoreFactoryDefaults();
    rollerMotor.restoreFactoryDefaults();

    conveyorMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    rollerMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

    conveyorMotor.setIdleMode(IdleMode.kBrake);
    rollerMotor.setInverted(true);

    rollerEncoder = rollerMotor.getEncoder(EncoderType.kNoSensor, 0);
    conveyorEncoder = conveyorMotor.getEncoder();

    // rollerEncoder.setPosition(0);
    conveyorEncoder.setPosition(0);

  }

  public void runConveyor(double speed) {
    if (speed <= 0) {
      if (speed <= -0.65)
        speed = -0.65;
    } else {
      if (speed >= 0.33)
        speed = 0.33;
    }
    conveyorMotor.set(speed);
    SmartDashboard.putNumber("Conveyor Speed", speed);
  }

  public void resetConveyorEncoder() {
    conveyorEncoder.setPosition(0);
  }

  public double getConveyorEncoderPosition() {
    return conveyorEncoder.getPosition();
  }

  public void rollerOnOff() {
    rollersRunning = !rollersRunning;
    if (rollersRunning) {
      rollerMotor.set(LowScoringConstants.ROLLER_SPEED * rollerDirection);
    } else {
      rollerMotor.stopMotor();
    }
  }

  public void rollerChangeDirection() {
    rollerDirection *= -1;
    rollerOnOff();
    rollerOnOff();
    SmartDashboard.putNumber("Rolling Direction", rollerDirection);
  }

  public double getConveyorStep() {
    return conveyorStep.getDouble(defaultConveyorStep);
  }

  public void stopConveyor() {
    conveyorMotor.stopMotor();
  }

  public void rollerMotorOn() {
    rollerMotor.set(LowScoringConstants.ROLLER_SPEED);
  }

  public void rollerMotorOff() {
    rollerMotor.stopMotor();
  }

  public void setRollerSpeed(double speed) {
    rollerMotor.set(speed);
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Roller Encoder", rollerEncoder.getPosition());
    SmartDashboard.putNumber("Conveyor Encoder", conveyorEncoder.getPosition());
  }
}
