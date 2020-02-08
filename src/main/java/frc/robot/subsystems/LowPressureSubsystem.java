/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LowPressureConstants;

public class LowPressureSubsystem extends SubsystemBase {

  private AnalogInput pressureSensor = new AnalogInput(LowPressureConstants.PRESSURE_SENSOR_CHANNEL);

  public LowPressureSubsystem() {

  }

  @Override
  public void periodic() {
    double pressure = 250.0 * pressureSensor.getVoltage() / 5.0 - 25.0;

    SmartDashboard.putNumber("Low Pressure", pressure);
  }
}
