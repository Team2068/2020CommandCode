/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LowScoringSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class GetPowercellsInTrench extends ParallelRaceGroup {
  /**
   * Creates a new GetPowercellsInTrench.
   */
  public GetPowercellsInTrench(DriveSubsystem driveSubsystem, LowScoringSubsystem lowScoringSubsystem) {
    addCommands(new DriveDistance(driveSubsystem, .37, 49), new AcquirePowerCells(lowScoringSubsystem));
  }
}
