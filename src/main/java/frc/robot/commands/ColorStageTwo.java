/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;

public class ColorStageTwo extends CommandBase {
  /**
   * Creates a new ColorStageTwo.
   */

  ColorSensor colorSensor;
  ControlPanelSubsystem controlPanel;

  private boolean useColor = true;
  private int rotationCount;
  private int changeCount;
  private int targetRotation = 4;
  private Color previousColor;
  private Color targetColor;

  public ColorStageTwo(ColorSensor c, ControlPanelSubsystem p, boolean b) {
    colorSensor = c;
    controlPanel = p;
    useColor = b;

    addRequirements(colorSensor);
    addRequirements(controlPanel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetColor = colorSensor.getFieldColor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (useColor) {
      boolean spin = true;
      controlPanel.controlPanelMotor.set(10);
      while (spin) {
        spin = shouldContinueColor(colorSensor.getSensorColor());
      }
      controlPanel.controlPanelMotor.stopMotor();
    } else {
      spinRPM();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private boolean shouldContinueColor(Color detected) {
    boolean sameColor = colorSensor.isSameColor(detected, previousColor);
    if (!sameColor) { // Color is changed
      changeCount += 1; // Add 1 to change counter
      if (changeCount >= 7) { // If 1 rotation done ( 7 changes ) add 1 to rotation
        changeCount = 0;
        rotationCount += 1;
      }
      previousColor = detected;
    }

    if (rotationCount >= targetRotation && detected == targetColor) { // If reached target color and target rotation
      return false; // Stop spinning
    }
    return true; // Continue Spinning
  }

  private void spinRPM() {
    double rotations = controlPanel.controlPanelMotorEncoder.getPosition();
    controlPanel.controlPanelMotor.set(10);
    while (rotations < 32.0f) {
    }
    controlPanel.controlPanelMotor.stopMotor();
  }
}
