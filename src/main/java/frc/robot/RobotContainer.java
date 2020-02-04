/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

  // private final ExampleCommand m_autoCommand = new ExampleCommand();

  // private final Limelight m_limelight = new Limelight();
  // private final DrivetrainSubsystems m_drivetrain = new DrivetrainSubsystems();
  private final ColorSensor colorSensor = new ColorSensor();

  private final XboxController driverController = new XboxController(0);
  private final XboxController m_mechanismController = new XboxController(2);
  private final Joystick m_rightJoystick = new Joystick(0);
  private final Joystick m_leftJoystick = new Joystick(1);  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveSubsystem.setDefaultCommand(new RunCommand(() -> driveSubsystem
      .tankDrive(driverController.getY(GenericHID.Hand.kLeft), 
        driverController.getY(GenericHID.Hand.kRight)), driveSubsystem));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
   new JoystickButton(driverController, Button.kBumperRight.value)
   .whenPressed(() -> driveSubsystem.invertTankDrive());
      
   new JoystickButton(driverController, Button.kBack.value)
   .whenPressed(() -> driveSubsystem.fullSend());

   new JoystickButton(driverController, Button.kBack.value)
   .whenReleased(() -> driveSubsystem.normalSend()); 

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * +
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  //   // An ExampleCommand will run in autonomous
  //   return m_autoCommand;
  // }
}
