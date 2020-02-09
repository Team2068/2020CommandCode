/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.LowScoringConstants;

public class LowScoringSubsystem extends SubsystemBase {

  private CANSparkMax conveyorMotor = new CANSparkMax(LowScoringConstants.CONVEYOR_MOTOR, MotorType.kBrushless);
  private CANSparkMax rollerMotor = new CANSparkMax(LowScoringConstants.ROLLER_MOTOR, MotorType.kBrushless);
  private DoubleSolenoid lockSolenoid = new DoubleSolenoid(LowScoringConstants.FORWARD_CHANNEL, LowScoringConstants.REVERSE_CHANNEL);

  private boolean rollersRunning = false;
  private int rollerDirection = 1;
  private boolean pistonsForward = false;

  public LowScoringSubsystem() {

    conveyorMotor.restoreFactoryDefaults();
    rollerMotor.restoreFactoryDefaults();

    conveyorMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    rollerMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

    lockSolenoid.set(Value.kOff);

  }

  public void runConveyor(double speed) {
    conveyorMotor.set(speed);
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
  }

  public void openCloseLowScoring() {
    pistonsForward = !pistonsForward;
    if(pistonsForward){
      lockSolenoid.set(Value.kForward);
    }
    else {
      lockSolenoid.set(Value.kReverse);
    }
  }

  public void trapPowercells(){
    lockSolenoid.set(Value.kForward);
  }

  public void releasePowercells(){
    lockSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
