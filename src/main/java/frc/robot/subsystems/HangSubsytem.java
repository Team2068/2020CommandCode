/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.LiftConstants;

public class HangSubsytem extends SubsystemBase {

  private CANSparkMax liftMotor = new CANSparkMax(LiftConstants.LIFT_MOTOR, MotorType.kBrushless);

  private CANSparkMax winchMotor = new CANSparkMax(LiftConstants.WINCH_MOTOR, MotorType.kBrushless);

  /**
   * Creates a new LiftSubsystem.
   */
  public HangSubsytem() {

    liftMotor.restoreFactoryDefaults();
    winchMotor.restoreFactoryDefaults();

    liftMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    winchMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

  }

// public class XboxController extends GenericHID{

//     public XboxController(int port) {
//       super(port);
//       // TODO Auto-generated constructor stub
//     }

//     @Override
//     public double getX(Hand hand) {
//       // TODO Auto-generated method stub
//       return 0;
//     }

//     @Override
//     public double getY(Hand hand) {
//       // TODO Auto-generated method stub
//       return 0;
//     }
  
// }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
