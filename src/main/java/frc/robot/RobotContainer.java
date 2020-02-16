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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.InvertTankDrive;
import frc.robot.commands.LiftToHeight;
import frc.robot.commands.ResetLiftEncoder;
import frc.robot.commands.RollersChangeDirection;
import frc.robot.commands.RollersOnOff;
import frc.robot.commands.RunConveyor;
import frc.robot.commands.SlowOff;
import frc.robot.commands.SlowOn;
import frc.robot.commands.SpinControlPanel;
import frc.robot.commands.StopControlPanel;
import frc.robot.commands.TankDrive;
import frc.robot.commands.TurboOff;
import frc.robot.commands.TurboOn;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.LowPressureSubsystem;
import frc.robot.subsystems.LowScoringSubsystem;
import frc.robot.subsystems.Gyroscope;

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
  private final HangSubsystem hangSubsystem = new HangSubsystem();

  private final ColorSensor colorSensor = new ColorSensor();
  private final Limelight limelight = new Limelight(Constants.CameraMode.VISION, Constants.StreamMode.PIP_MAIN);
  private final Gyroscope gyro = new Gyroscope();
  private final LowPressureSubsystem lowPressureSubsystem = new LowPressureSubsystem();

  private final XboxController driverController = new XboxController(DriveConstants.driverController);
  private final XboxController mechanismController = new XboxController(DriveConstants.mechanismController);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    setUpSmartDashboardCommands();
    setSmartDashboardSubsystems();

    driveSubsystem.setDefaultCommand(new TankDrive(driveSubsystem, driverController.getY(GenericHID.Hand.kLeft),
        driverController.getY(GenericHID.Hand.kRight)));

    lowScoringSubsystem
        .setDefaultCommand(new RunConveyor(lowScoringSubsystem, mechanismController.getY(GenericHID.Hand.kLeft)));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // everything on the driverController
    new JoystickButton(driverController, Button.kY.value).whenPressed(new InvertTankDrive(driveSubsystem));

    new JoystickButton(driverController, ControllerConstants.RIGHT_TRIGGER).whenPressed(new TurboOn(driveSubsystem))
        .whenReleased(new TurboOff(driveSubsystem)); // sprint

    new JoystickButton(driverController, ControllerConstants.LEFT_TRIGGER).whenPressed(new SlowOn(driveSubsystem))
        .whenReleased(new SlowOff(driveSubsystem)); // 25% speed

    new JoystickButton(driverController, Button.kX.value).whenPressed(new LiftToHeight(hangSubsystem));

    new JoystickButton(driverController, Button.kB.value).whenPressed(() -> hangSubsystem.winchAndLowerLift());

    // everything on the mechanismController
    new JoystickButton(mechanismController, Button.kBumperRight.value)
        .whenPressed(new RollersOnOff(lowScoringSubsystem));

    new JoystickButton(mechanismController, Button.kA.value)
        .whenPressed(new RollersChangeDirection(lowScoringSubsystem));

    new JoystickButton(mechanismController, Button.kB.value)
        .whenPressed(() -> controlPanelSubsystem.engageControlPanel());

    new JoystickButton(mechanismController, Button.kX.value).whenPressed(() -> {
      int stream = limelight.getStream();
      switch (stream) {
      case Constants.StreamMode.PIP_SECONDARY:
        limelight.setStream(Constants.StreamMode.PIP_MAIN);
        break;
      case Constants.StreamMode.PIP_MAIN:
        limelight.setStream(Constants.StreamMode.PIP_SECONDARY);
        break;
      }
    });

    new JoystickButton(mechanismController, Button.kA.value).whenPressed(() -> {
      int mode = limelight.getMode();
      if (mode == Constants.CameraMode.DRIVER) {
        limelight.setMode(Constants.CameraMode.VISION);
      } else {
        limelight.setMode(Constants.CameraMode.DRIVER);
      }
    });
  }

  private void setUpSmartDashboardCommands() {
    SmartDashboard.putData("Spin Control Panel", new SpinControlPanel(controlPanelSubsystem));
    SmartDashboard.putData("Stop Control Panel", new StopControlPanel(controlPanelSubsystem));
    SmartDashboard.putData("Reset Lift Encoder", new ResetLiftEncoder(hangSubsystem));
  }

  private void setSmartDashboardSubsystems() {
    SmartDashboard.putData(driveSubsystem);
    SmartDashboard.putData(hangSubsystem);
    SmartDashboard.putData(lowScoringSubsystem);
    SmartDashboard.putData(controlPanelSubsystem);
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
