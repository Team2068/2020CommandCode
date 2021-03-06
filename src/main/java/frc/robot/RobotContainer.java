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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.AdvanceConveyor;
import frc.robot.commands.DriveAndScoreLow;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.EngageControlPanelWheel;
import frc.robot.commands.InvertTankDrive;
import frc.robot.commands.LiftToHeight;
import frc.robot.commands.LowerLift;
import frc.robot.commands.ResetLiftEncoder;
import frc.robot.commands.RollersIn;
import frc.robot.commands.RollersOff;
import frc.robot.commands.RollersOut;
import frc.robot.commands.RunConveyor;
import frc.robot.commands.ScoreLowAndTrench;
import frc.robot.commands.ScoreStageThree;
import frc.robot.commands.ScoreStageTwoColorSwitch;
import frc.robot.commands.ScoreStageTwoRotations;
import frc.robot.commands.SlowOff;
import frc.robot.commands.SlowOn;
import frc.robot.commands.SpinControlPanel;
import frc.robot.commands.StartWinch;
import frc.robot.commands.StopControlPanel;
import frc.robot.commands.StopLift;
import frc.robot.commands.StopWinch;
import frc.robot.commands.StraightToTrench;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ToggleCameraMode;
import frc.robot.commands.ToggleStreamMode;
import frc.robot.commands.TurboOff;
import frc.robot.commands.TurboOn;
import frc.robot.commands.TurnDegrees;
import frc.robot.commands.WithdrawConveyor;
import frc.robot.subsystems.ColorSensor;
// import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveSubsystem;
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
  private final Limelight limelight = new Limelight(Constants.CameraMode.DRIVER, Constants.StreamMode.PIP_MAIN);
  private final LowPressureSubsystem lowPressureSubsystem = new LowPressureSubsystem();

  private final XboxController driverController = new XboxController(DriveConstants.driverController);
  private final XboxController mechanismController = new XboxController(DriveConstants.mechanismController);

  private SendableChooser<Command> autonomousChooser = new SendableChooser<Command>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    setUpSmartDashboardCommands();
    setSmartDashboardSubsystems();
    setUpAutonomousChooser();

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
    Trigger driverRightTrigger = new Trigger(() -> driverController
        .getRawAxis(ControllerConstants.RIGHT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    Trigger driverLeftTrigger = new Trigger(() -> driverController
        .getRawAxis(ControllerConstants.LEFT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    JoystickButton driverRightBumper = new JoystickButton(driverController, Button.kBumperRight.value);
    JoystickButton driverLeftBumper = new JoystickButton(driverController, Button.kBumperLeft.value);
    POVButton driverDPadUp = new POVButton(driverController, ControllerConstants.POV_ANGLE_UP);
    JoystickButton driverY = new JoystickButton(driverController, Button.kY.value);
    JoystickButton driverX = new JoystickButton(driverController, Button.kX.value);
    JoystickButton driverB = new JoystickButton(driverController, Button.kB.value);
    JoystickButton driverA = new JoystickButton(driverController, Button.kA.value);
    Trigger mechanismRightTrigger = new Trigger(() -> mechanismController
        .getRawAxis(ControllerConstants.RIGHT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    Trigger mechanismLeftTrigger = new Trigger(() -> mechanismController
        .getRawAxis(ControllerConstants.LEFT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    JoystickButton mechanismLeftBumper = new JoystickButton(mechanismController, Button.kBumperLeft.value);
    JoystickButton mechanismRightBumper = new JoystickButton(mechanismController, Button.kBumperRight.value);
    POVButton mechanismDPadLeft = new POVButton(mechanismController, ControllerConstants.POV_ANGLE_LEFT);
    POVButton mechanismDPadRight = new POVButton(mechanismController, ControllerConstants.POV_ANGLE_RIGHT);
    JoystickButton mechanismY = new JoystickButton(mechanismController, Button.kY.value);
    JoystickButton mechanismA = new JoystickButton(mechanismController, Button.kA.value);
    JoystickButton mechanismB = new JoystickButton(mechanismController, Button.kB.value);
    JoystickButton mechanismX = new JoystickButton(mechanismController, Button.kX.value);

    // driverController
    driverRightTrigger.whenActive(new TurboOn(driveSubsystem)).whenInactive(new TurboOff(driveSubsystem));
    driverLeftTrigger.whenActive(new SlowOn(driveSubsystem)).whenInactive(new SlowOff(driveSubsystem));
    driverDPadUp.whenActive(new StartWinch(hangSubsystem)).whenInactive(new StopWinch(hangSubsystem));
    driverX.whenPressed(new InvertTankDrive(driveSubsystem));
    driverY.whenPressed(new LiftToHeight(hangSubsystem));
    driverA.whenHeld(new LowerLift(hangSubsystem)).whenReleased(new StopLift(hangSubsystem));

    // mechanismController
    mechanismLeftTrigger.whenActive(new AdvanceConveyor(lowScoringSubsystem));
    mechanismLeftBumper.whenPressed(new WithdrawConveyor(lowScoringSubsystem));
    mechanismDPadRight.whenPressed(new ScoreStageTwoRotations(controlPanelSubsystem));
    // mechanismDPadLeft.whenPressed(new ScoreStageThree(colorSensor,
    // controlPanelSubsystem));
    mechanismX.whenHeld(new RollersIn(lowScoringSubsystem)).whenReleased(new RollersOff(lowScoringSubsystem));
    mechanismB.whenHeld(new RollersOut(lowScoringSubsystem)).whenReleased(new RollersOff(lowScoringSubsystem));
    mechanismY.whenPressed(new EngageControlPanelWheel(controlPanelSubsystem));
    // mechanismX.whenPressed(new ToggleStreamMode(limelight));
    // mechanismA.whenPressed(new ToggleCameraMode(limelight));
  }

  private void setUpSmartDashboardCommands() {
    Dashboard.putDebugData("Spin Control Panel", new SpinControlPanel(controlPanelSubsystem));
    Dashboard.putDebugData("Stop Control Panel", new StopControlPanel(controlPanelSubsystem));
    Dashboard.putDebugData("Reset Lift Encoder", new ResetLiftEncoder(hangSubsystem));
    Dashboard.putDebugData("Stage 2 Color", new ScoreStageTwoColorSwitch(colorSensor, controlPanelSubsystem));
    Dashboard.putDebugData("Stage 2 Rotations", new ScoreStageTwoRotations(controlPanelSubsystem));
    Dashboard.putDebugData("Stage 3", new ScoreStageThree(colorSensor, controlPanelSubsystem));
    SmartDashboard.putData("Toggle Camera Mode", new ToggleCameraMode(limelight));
    SmartDashboard.putData("Toggle Stream Mode", new ToggleStreamMode(limelight));
    // SmartDashboard.putData("Raise Lift", new RaiseLift(hangSubsystem));
    // SmartDashboard.putData("Stop Lifting", new StopLift(hangSubsystem));
    SmartDashboard.putData("Start Winch", new StartWinch(hangSubsystem));
    SmartDashboard.putData("Stop Winch", new StopWinch(hangSubsystem));
  }

  private void setSmartDashboardSubsystems() {
    Dashboard.putDebugData("Drive Subsystem", driveSubsystem);
    Dashboard.putDebugData("Hang Subsystem", hangSubsystem);
    Dashboard.putDebugData("Low Scoring Subsystem", lowScoringSubsystem);
    Dashboard.putDebugData("Control Panel", controlPanelSubsystem);
    Dashboard.putDebugData("Low Pressure Subsystem", lowPressureSubsystem);
  }

  private void setUpAutonomousChooser() {
    autonomousChooser.setDefaultOption("Leave Line Forwads", new DriveDistance(driveSubsystem, .25, 48));
    autonomousChooser.addOption("Leave Line Backwards", new DriveDistance(driveSubsystem, -.25, 48));
    autonomousChooser.addOption("Drive and Low Score", new DriveAndScoreLow(driveSubsystem, lowScoringSubsystem));
    autonomousChooser.addOption("Score and Trench", new ScoreLowAndTrench(driveSubsystem, lowScoringSubsystem));
    autonomousChooser.addOption("Straight to Trench", new StraightToTrench(driveSubsystem, lowScoringSubsystem));
    SmartDashboard.putData("Autonomous Mode", autonomousChooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * +
   * 
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autonomousChooser.getSelected();
  }
}
