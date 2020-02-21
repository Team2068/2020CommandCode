/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;

public class ToggleCameraMode extends InstantCommand {
    /**
     * Creates a new ToggleCameraMode.
     */
    Limelight limelight;

    public ToggleCameraMode(Limelight limelight) {
        this.limelight = limelight;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(limelight);
    }

    @Override
    public void execute() {
        int stream = limelight.getStream();
        switch (stream) {
        case Constants.StreamMode.PIP_SECONDARY:
            limelight.setStream(Constants.StreamMode.PIP_MAIN);
        case Constants.StreamMode.PIP_MAIN:
            limelight.setStream(Constants.StreamMode.PIP_SECONDARY);
        default:
            break;
        }
    }
}
