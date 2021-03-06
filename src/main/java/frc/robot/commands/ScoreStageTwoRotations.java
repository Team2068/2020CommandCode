/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanelSubsystem;

public class ScoreStageTwoRotations extends CommandBase {
  /**
   * Creates a new ScoreStageTwoRotations.
   */
  ControlPanelSubsystem controlPanel;

  private double rotations;
  private double velocity;

  public ScoreStageTwoRotations(ControlPanelSubsystem p) {
    controlPanel = p;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(controlPanel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    controlPanel.resetEncoders();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    controlPanel.setMotorSpeed(1);
    rotations = controlPanel.getRotations();
    velocity = controlPanel.getVelocity();
    SmartDashboard.putNumber("Panel RPM", velocity / 8.f); // estimation
    SmartDashboard.putNumber("Panel Rotations", rotations);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    controlPanel.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(rotations) >= Math.abs(32.0f);
  }
}
