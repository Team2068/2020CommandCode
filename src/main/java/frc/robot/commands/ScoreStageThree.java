/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;

public class ScoreStageThree extends CommandBase {
  /**
   * Creates a new ScoreStageThree.
   */

  private ColorSensor colorSensor;
  private ControlPanelSubsystem controlPanel;

  private Color detected;
  private Color actual;
  private Color targetColor;
  private double velocity;

  public ScoreStageThree(ColorSensor c, ControlPanelSubsystem p) {
    colorSensor = c;
    controlPanel = p;

    // Use addRequirements() here to declare subsystem dependencies.
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
    controlPanel.setMotorSpeed(10);
    velocity = controlPanel.getVelocity();
    detected = colorSensor.getSensorColor();
    SmartDashboard.putNumber("Panel RPM", velocity / 8.f);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    controlPanel.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // color we want is 2 ahead, so we need to go before
    actual = ColorSensor.colors.get(colorSensor.getColorIndex(targetColor) - 2);
    return colorSensor.isSameColor(actual, detected);
  }
}
