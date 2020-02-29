/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.LowScoringSubsystem;

public class WithdrawConveyor extends CommandBase {
  private LowScoringSubsystem lowScoringSubsystem;

  public WithdrawConveyor(LowScoringSubsystem lowScoringSubsystem) {
    this.lowScoringSubsystem = lowScoringSubsystem;
    addRequirements(lowScoringSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    lowScoringSubsystem.resetConveyorEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lowScoringSubsystem.runConveyor(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lowScoringSubsystem.stopConveyor();
    lowScoringSubsystem.resetConveyorEncoder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double currentPosition = Math.abs(lowScoringSubsystem.getConveyorEncoderPosition());
    double conveyorPosition = Math.abs(lowScoringSubsystem.getConveyorStep());
    return currentPosition >= conveyorPosition;
  }
}
