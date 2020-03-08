/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class TurnDegrees extends CommandBase {
  private double defaultTargetAngle = 45;
  private double defaultDriveSpeed = .33;
  /**
   * Creates a new TurnDegrees.
   */
  private DriveSubsystem driveSubsystem;
  // private ShuffleboardTab tab = Shuffleboard.getTab("Autonomous");
  // private NetworkTableEntry targetAngle = tab.add("Target Angle",
  // defaultTargetAngle).getEntry();
  // private NetworkTableEntry driveSpeed = tab.add("defaultDriveSpeed",
  // defaultDriveSpeed).getEntry();

  // public TurnDegrees(DriveSubsystem driveSubsystem) {
  // this.driveSubsystem = driveSubsystem;
  // addRequirements(driveSubsystem);
  // }

  public TurnDegrees(DriveSubsystem driveSubsystem, double driveSpeed, double targetAngle) {
    this.driveSubsystem = driveSubsystem;
    defaultDriveSpeed = driveSpeed;
    defaultTargetAngle = targetAngle;
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveSubsystem.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.tankDrive(0, defaultDriveSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.arcadeDrive(0, 0);
    driveSubsystem.resetGyro();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(driveSubsystem.getAngle()) >= Math.abs(defaultTargetAngle);
  }
}
