/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private CANSparkMax backRight = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax middleRight = new CANSparkMax(0, MotorType.kBrushless);
  private CANSparkMax frontRight = new CANSparkMax(6, MotorType.kBrushless);

  private CANSparkMax backLeft = new CANSparkMax(2, MotorType.kBrushless);
  private CANSparkMax middleLeft = new CANSparkMax(0, MotorType.kBrushless);
  private CANSparkMax frontLeft = new CANSparkMax(5, MotorType.kBrushless);

  private CANEncoder backRightEncoder = backRight.getEncoder();
  private CANEncoder middleRightEncoder = middleRight.getEncoder();
  private CANEncoder frontRightEncoder = frontRight.getEncoder();

  private CANEncoder backLeftEncoder = backLeft.getEncoder();
  private CANEncoder middleLeftEncoder = middleLeft.getEncoder();
  private CANEncoder frontLeftEncoder = frontLeft.getEncoder();

  // private DoubleSolenoid rightSwitch = new DoubleSolenoid(2, 3);
  // private DoubleSolenoid leftSwitch = new DoubleSolenoid(4, 5);

  private XboxController controller = new XboxController(0);

  private double speedMod = .1;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);
    frontRight.setMotorType(MotorType.kBrushless);
    middleRight.setMotorType(MotorType.kBrushless);
    backRight.setMotorType(MotorType.kBrushless);
    frontLeft.setMotorType(MotorType.kBrushless);
    middleLeft.setMotorType(MotorType.kBrushless);
    backLeft.setMotorType(MotorType.kBrushless);

    frontLeft.setInverted(false);
    middleLeft.setInverted(false);
    backLeft.setInverted(false);
    frontRight.setInverted(true);
    middleRight.setInverted(true);
    backRight.setInverted(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
