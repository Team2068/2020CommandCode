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

public class ColorStageThree extends CommandBase {
  /**
   * Creates a new ColorStageThree.
   */

  private final ColorSensor colorSensor;
  private final ControlPanelSubsystem controlPanel;

  Color targetColor;
  Color detected;

  public ColorStageThree(ColorSensor c, ControlPanelSubsystem p) {
    colorSensor = c;
    controlPanel = p;

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
    detected = colorSensor.getSensorColor();
    Color actual = ColorSensor.colors.get(colorSensor.getColorIndex(detected) - 2);
    // color we want is 2 ahead, so we need to go before
    controlPanel.controlPanelMotor.set(10);
    while (!colorSensor.isSameColor(detected, actual)) {
    }
    controlPanel.controlPanelMotor.stopMotor();
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
}
