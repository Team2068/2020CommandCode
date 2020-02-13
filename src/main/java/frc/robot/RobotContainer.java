/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.LowPressureSubsystem;
import frc.robot.subsystems.LowScoringSubsystem;
import frc.robot.subsystems.Gyroscope;

import frc.robot.commands.ColorCount;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final LowScoringSubsystem lowScoringSubsystem = new LowScoringSubsystem();
  private final ControlPanelSubsystem controlPanelSubsystem = new ControlPanelSubsystem();

  private final ColorSensor colorSensor = new ColorSensor();
  private final Limelight limelight = new Limelight(Constants.CameraMode.VISION, Constants.StreamMode.PIP_MAIN);
  private final Gyroscope gyro = new Gyroscope();
  private final LowPressureSubsystem lowPressureSubsystem = new LowPressureSubsystem();

  private final XboxController driverController = new XboxController(DriveConstants.driverController);
  private final XboxController mechanismController = new XboxController(DriveConstants.mechanismController);

  private final ColorCount colorCount = new ColorCount(colorSensor, controlPanelSubsystem);
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveSubsystem
        .setDefaultCommand(new RunCommand(() -> driveSubsystem.tankDrive(driverController.getY(GenericHID.Hand.kLeft),
            driverController.getY(GenericHID.Hand.kRight)), driveSubsystem));

    lowScoringSubsystem.setDefaultCommand(new RunCommand(
        () -> lowScoringSubsystem.runConveyor(mechanismController.getY(GenericHID.Hand.kLeft)), lowScoringSubsystem));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // everything on the driverController
    new JoystickButton(driverController, Button.kY.value).whenPressed(() -> driveSubsystem.invertTankDrive());

    new JoystickButton(driverController, ControllerConstants.RIGHT_TRIGGER).whenPressed(() -> driveSubsystem.turboOn())
        .whenReleased(() -> driveSubsystem.turboOff());

    new JoystickButton(driverController, Button.kX.value).whenPressed(() -> {
      final int stream = limelight.getStream();
      switch(stream) {
        case Constants.StreamMode.PIP_SECONDARY:
          limelight.setStream(Constants.StreamMode.PIP_MAIN);
          break;
        case Constants.StreamMode.PIP_MAIN:
            limelight.setStream(Constants.StreamMode.PIP_SECONDARY);
            break;
      }
    });

    new JoystickButton(driverController, Button.kB.value).whenPressed(() -> {
      final int mode = limelight.getMode();
      if (mode == Constants.CameraMode.DRIVER) {
        limelight.setMode(Constants.CameraMode.VISION);
      } else {
        limelight.setMode(Constants.CameraMode.DRIVER);
      }
    });

    // everything on the mechanismController
    new JoystickButton(mechanismController, Button.kY.value).whenPressed(() -> lowScoringSubsystem.rollerOnOff());

    new JoystickButton(mechanismController, Button.kA.value)
        .whenPressed(() -> lowScoringSubsystem.rollerChangeDirection());

    new JoystickButton(mechanismController, Button.kBumperRight.value)
        .whenPressed(() -> lowScoringSubsystem.openCloseLowScoring());

    new JoystickButton(mechanismController, Button.kB.value)
        .whenPressed(() -> controlPanelSubsystem.engageControlPanel());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * +
   * 
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  // // An ExampleCommand will run in autonomous
  // return m_autoCommand;
  // }
}
