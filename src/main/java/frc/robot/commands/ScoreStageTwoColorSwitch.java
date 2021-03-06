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
import frc.robot.subsystems.Dashboard;

public class ScoreStageTwoColorSwitch extends CommandBase {
  /**
   * Creates a new ScoreStageTwoColorSwitch.
   */

  private ControlPanelSubsystem controlPanel;
  private ColorSensor colorSensor;

  private int rotationCount = 0;
  private int changeCount = 0;
  private int targetRotation = 4;
  private double velocity;
  private Color previous;
  private Color detected;

  public ScoreStageTwoColorSwitch(ColorSensor c, ControlPanelSubsystem p) {
    colorSensor = c;
    controlPanel = p;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(colorSensor);
    addRequirements(controlPanel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    detected = colorSensor.getSensorColor();
    previous = detected;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    controlPanel.setMotorSpeed(10);
    detected = colorSensor.getSensorColor();
    velocity = controlPanel.getVelocity();
    boolean sameColor = colorSensor.isSameColor(detected, previous);
    if (!sameColor) {
      changeCount += 1;
      if (changeCount >= 7) {
        changeCount = 0;
        rotationCount += 1;
      }
      previous = detected;
    }
    Dashboard.putDebugNumber("Panel RPM", velocity / 8.f);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    controlPanel.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return rotationCount >= targetRotation;
  }
}
