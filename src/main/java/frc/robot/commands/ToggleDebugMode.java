/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ToggleDebugMode extends InstantCommand {
  /**
   * Creates a new ToggleDebugMode.
   */
  @Override
  public void execute() {
    Constants.debug = !Constants.debug;
  }
}