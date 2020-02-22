/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.AdvanceConveyor;
import frc.robot.commands.EngageControlPanelWheel;
import frc.robot.commands.InvertTankDrive;
import frc.robot.commands.LiftToHeight;
import frc.robot.commands.RaiseLift;
import frc.robot.commands.ResetLiftEncoder;
import frc.robot.commands.RollerDrive;
import frc.robot.commands.RollersChangeDirection;
import frc.robot.commands.RollersOff;
import frc.robot.commands.RollersOn;
import frc.robot.commands.RollersOnOff;
import frc.robot.commands.RunConveyor;
import frc.robot.commands.ScoreStageThree;
import frc.robot.commands.ScoreStageTwoColorSwitch;
import frc.robot.commands.ScoreStageTwoRotations;
import frc.robot.commands.SlowOff;
import frc.robot.commands.SlowOn;
import frc.robot.commands.SpinControlPanel;
import frc.robot.commands.StopControlPanel;
import frc.robot.commands.StopLift;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ToggleCameraMode;
import frc.robot.commands.ToggleStreamMode;
import frc.robot.commands.TurboOff;
import frc.robot.commands.TurboOn;
import frc.robot.subsystems.ColorSensor;
// import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Gyroscope;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.LowPressureSubsystem;
import frc.robot.subsystems.LowScoringSubsystem;

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
    debugJoystics();

    driveSubsystem.setDefaultCommand(new TankDrive(driveSubsystem, driverController));

    lowScoringSubsystem.setDefaultCommand(new RunConveyor(lowScoringSubsystem, mechanismController));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton mechanismY = new JoystickButton(mechanismController, Button.kY.value);
    JoystickButton mechanismRightBumper = new JoystickButton(mechanismController, Button.kBumperRight.value);
    JoystickButton mechanismA = new JoystickButton(mechanismController, Button.kA.value);
    JoystickButton mechanismB = new JoystickButton(mechanismController, Button.kB.value);
    JoystickButton mechanismX = new JoystickButton(mechanismController, Button.kX.value);
    JoystickButton driverY = new JoystickButton(driverController, Button.kY.value);
    JoystickButton driverRightTrigger = new JoystickButton(driverController, ControllerConstants.RIGHT_TRIGGER);
    JoystickButton driverLeftTrigger = new JoystickButton(driverController, ControllerConstants.LEFT_TRIGGER);
    JoystickButton driverX = new JoystickButton(driverController, Button.kX.value);
    JoystickButton driverB = new JoystickButton(driverController, Button.kB.value);
    JoystickButton driverA = new JoystickButton(driverController, Button.kA.value);

    // driverController
    driverY.whenPressed(new InvertTankDrive(driveSubsystem));
    driverRightTrigger.whenPressed(new TurboOn(driveSubsystem)).whenReleased(new TurboOff(driveSubsystem)); // sprint
    driverLeftTrigger.whenPressed(new SlowOn(driveSubsystem)).whenReleased(new SlowOff(driveSubsystem)); // 25% speed
    driverX.whenPressed(new LiftToHeight(hangSubsystem));
    driverB.whenPressed(() -> hangSubsystem.winchAndLowerLift());
    driverA.whenPressed(new RollersOn(lowScoringSubsystem));

    // mechanismController
    mechanismY.whenPressed(new AdvanceConveyor(lowScoringSubsystem));
    mechanismRightBumper.whenPressed(new RollersOnOff(lowScoringSubsystem));
    mechanismA.whenPressed(new RollersChangeDirection(lowScoringSubsystem));
    mechanismB.whenPressed(new EngageControlPanelWheel(controlPanelSubsystem));
    mechanismX.whenPressed(new ToggleStreamMode(limelight));
    mechanismA.whenPressed(new ToggleCameraMode(limelight));
  }

  private void setUpSmartDashboardCommands() {
    SmartDashboard.putData("Spin Control Panel", new SpinControlPanel(controlPanelSubsystem));
    SmartDashboard.putData("Stop Control Panel", new StopControlPanel(controlPanelSubsystem));
    SmartDashboard.putData("Reset Lift Encoder", new ResetLiftEncoder(hangSubsystem));
    SmartDashboard.putData("Roller On/Off", new RollersOnOff(lowScoringSubsystem));
    SmartDashboard.putData("Stage 2 Color", new ScoreStageTwoColorSwitch(colorSensor, controlPanelSubsystem));
    SmartDashboard.putData("Stage 2 Rotations", new ScoreStageTwoRotations(controlPanelSubsystem));
    SmartDashboard.putData("Stage 3", new ScoreStageThree(colorSensor, controlPanelSubsystem));
    SmartDashboard.putData("Toggle Camera Mode", new ToggleCameraMode(limelight));
    SmartDashboard.putData("Toggle Stream Mode", new ToggleStreamMode(limelight));
    SmartDashboard.putData("Rollers On", new RollersOn(lowScoringSubsystem));
    SmartDashboard.putData("Rollers Off", new RollersOff(lowScoringSubsystem));
    SmartDashboard.putData("Raise Lift", new RaiseLift(hangSubsystem));
    SmartDashboard.putData("Stop Lifting", new StopLift(hangSubsystem));
  }

  private void setSmartDashboardSubsystems() {
    SmartDashboard.putData(driveSubsystem);
    SmartDashboard.putData(hangSubsystem);
    SmartDashboard.putData(lowScoringSubsystem);
    SmartDashboard.putData(controlPanelSubsystem);
    SmartDashboard.putData(gyro);
    SmartDashboard.putData(lowPressureSubsystem);
  }

  private void debugJoystics() {
    SmartDashboard.putNumber("DriverJoyR", driverController.getY(GenericHID.Hand.kRight));
    SmartDashboard.putNumber("DriverJoyL", driverController.getY(GenericHID.Hand.kLeft));

    SmartDashboard.putNumber("MechJoyR", mechanismController.getY(GenericHID.Hand.kRight));
    SmartDashboard.putNumber("MechJoyL", mechanismController.getY(GenericHID.Hand.kLeft));
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
