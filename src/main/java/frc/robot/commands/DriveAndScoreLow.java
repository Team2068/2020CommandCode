/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.LowScoringConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LowScoringSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveAndScoreLow extends SequentialCommandGroup {

  public DriveAndScoreLow(DriveSubsystem driveSubsystem, LowScoringSubsystem lowScoringSubsystem) {
    double driveSpeed = .5;
    double approachSpeed = .25;
    int encoderToWall = 42;
    int encoderPastLine = 50;
    int conveyorSpeed = 1;
    int conveyorEncoder = 70;
    addCommands(new DriveDistance(driveSubsystem, -approachSpeed, encoderToWall),
        new DriveDistance(driveSubsystem, -.17, 3),
    int conveyorEncoder = 120;
    addCommands(new DriveDistance(driveSubsystem, -driveSpeed, encoderToWall),
        new ConveyorScore(lowScoringSubsystem, -conveyorSpeed, conveyorEncoder),
        new DriveDistance(driveSubsystem, driveSpeed, encoderPastLine));
  }
}
