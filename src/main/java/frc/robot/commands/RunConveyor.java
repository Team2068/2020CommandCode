/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LowScoringSubsystem;

public class RunConveyor extends CommandBase {

  private LowScoringSubsystem lowScoringSubsystem;
  private XboxController controller;

  public RunConveyor(LowScoringSubsystem lowScoringSubsystem, XboxController controller) {
    this.lowScoringSubsystem = lowScoringSubsystem;
    this.controller = controller;
    addRequirements(lowScoringSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = controller.getY(Hand.kLeft);
    lowScoringSubsystem.runConveyor(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lowScoringSubsystem.runConveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
