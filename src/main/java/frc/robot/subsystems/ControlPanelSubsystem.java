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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ControlPanelConstants;

public class ControlPanelSubsystem extends SubsystemBase {

  private DoubleSolenoid controlPanelSolenoid = new DoubleSolenoid(ControlPanelConstants.FORWARD_CHANNEL,
      ControlPanelConstants.REVERSE_CHANNEL);
  private CANSparkMax controlPanelMotor = new CANSparkMax(ControlPanelConstants.CONTROL_PANEL_MOTOR,
      MotorType.kBrushless);

  private CANEncoder controlPanelEncoder;
  private boolean pistonsForward = false;

  public ControlPanelSubsystem() {
    controlPanelSolenoid.set(Value.kOff);
    controlPanelMotor.restoreFactoryDefaults();
    controlPanelMotor.setSmartCurrentLimit(ControlPanelConstants.CONTROL_PANEL_LIMIT);

    controlPanelEncoder = controlPanelMotor.getEncoder();
  }

  public void setMotorSpeed(double speed) {
    controlPanelMotor.set(speed);
  }

  public void stopMotor() {
    controlPanelMotor.stopMotor();
  }

  public void engageControlPanel() {
    pistonsForward = !pistonsForward;
    if (pistonsForward) {
      wheelUp();
    } else {
      wheelDown();
    }
  }

  public void wheelUp() {
    controlPanelSolenoid.set(Value.kForward);
  }

  public void wheelDown() {
    controlPanelSolenoid.set(Value.kReverse);
  }

  public double getRotations() {
    return controlPanelEncoder.getPosition();
  }

  public double getVelocity() {
    return controlPanelEncoder.getVelocity();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
