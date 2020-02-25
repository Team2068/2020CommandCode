/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.HangConstants;
import frc.robot.subsystems.HangSubsystem;

public class LowerLift extends CommandBase {
  private HangSubsystem hangSubsystem;

  public LowerLift(HangSubsystem hangSubsystem) {
    this.hangSubsystem = hangSubsystem;
    addRequirements(hangSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hangSubsystem.resetEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hangSubsystem.lowerLift();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hangSubsystem.stopLifting();
    hangSubsystem.resetEncoder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(hangSubsystem.liftPosition()) >= HangConstants.BOTTOM_LIFT_ENCODER;
  }
}
