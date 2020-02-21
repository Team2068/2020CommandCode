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

public class ToggleStreamMode extends InstantCommand {
    /**
     * Creates a new ToggleStreamMode.
     */
    Limelight limelight;
    int mode;

    public ToggleStreamMode(Limelight limelight) {
        this.limelight = limelight;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(limelight);
    }

    @Override
    public void execute() {
        int mode = limelight.getMode();
        if (mode == Constants.CameraMode.DRIVER) {
            limelight.setMode(Constants.CameraMode.VISION);
        } else {
            limelight.setMode(Constants.CameraMode.DRIVER);
        }
    }
}