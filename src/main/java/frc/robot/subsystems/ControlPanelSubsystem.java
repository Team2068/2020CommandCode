/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlPanelConstants;

public class ControlPanelSubsystem extends SubsystemBase {
 
  public DoubleSolenoid controlPanelSolenoid = new DoubleSolenoid(ControlPanelConstants.FORWARD_CHANNEL, ControlPanelConstants.REVERSE_CHANNEL);
  public boolean pistonsForward = false;

  public ControlPanelSubsystem() {
    controlPanelSolenoid.set(Value.kOff);
  }

  public void engageControlPanel() {
    pistonsForward = !pistonsForward;
    if(pistonsForward){
      controlPanelSolenoid.set(Value.kForward);
    }
    else {
      controlPanelSolenoid.set(Value.kReverse);
    }
  }

  public void wheelUp(){
    controlPanelSolenoid.set(Value.kForward);
  }

  public void wheelDown(){
    controlPanelSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}