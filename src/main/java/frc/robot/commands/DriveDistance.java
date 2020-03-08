/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
  private DriveSubsystem driveSubsystem;
  private double speed;
  private double distance;

  public DriveDistance(DriveSubsystem driveSubsystem, double speed, double distance) {
    this.speed = speed;
    this.distance = distance;
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveSubsystem.resetDriveEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.tankDrive(speed, speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopDrive();
    driveSubsystem.resetDriveEncoders();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(driveSubsystem.getDriveEncoderPosition()) >= Math.abs(distance);
  }
}
