/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;

public class ToggleCameraMode extends CommandBase {
  /**
   * Creates a new ToggleCameraMode.
   */
  Limelight limelight;

  public ToggleCameraMode(Limelight limelight) {
    this.limelight = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    int stream = limelight.getStream();
    switch (stream) {
    case Constants.StreamMode.PIP_SECONDARY:
      limelight.setStream(Constants.StreamMode.PIP_MAIN);
      return true;
    case Constants.StreamMode.PIP_MAIN:
      limelight.setStream(Constants.StreamMode.PIP_SECONDARY);
      return true;
    }
    return false;
  }
}
