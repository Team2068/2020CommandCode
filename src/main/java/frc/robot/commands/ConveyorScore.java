/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LowScoringSubsystem;

public class ConveyorScore extends CommandBase {
  private LowScoringSubsystem lowScoringSubsystem;
  private DriveSubsystem driveSubsystem;
  private double speed;
  private double distance;

  public ConveyorScore(DriveSubsystem driveSubsystem, LowScoringSubsystem lowScoringSubsystem, double speed,
      double distance) {
    this.speed = speed;
    this.distance = distance;
    this.lowScoringSubsystem = lowScoringSubsystem;
    this.driveSubsystem = driveSubsystem;
    addRequirements(lowScoringSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveSubsystem.tankDrive(.25, .25);
    lowScoringSubsystem.resetConveyorEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lowScoringSubsystem.runConveyor(speed);
    driveSubsystem.tankDrive(-.25, -.25);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lowScoringSubsystem.stopConveyor();
    driveSubsystem.tankDrive(0, 0);
    lowScoringSubsystem.resetConveyorEncoder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(lowScoringSubsystem.getConveyorEncoderPosition()) >= Math.abs(distance);
  }
}
