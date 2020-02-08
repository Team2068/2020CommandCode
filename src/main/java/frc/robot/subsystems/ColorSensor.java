/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorSensor extends SubsystemBase {
  
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();

    private final Color BlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color GreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color RedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color YellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    String colorString;
    
    public ColorSensor() {
        m_colorMatcher.addColorMatch(BlueTarget);
        m_colorMatcher.addColorMatch(GreenTarget);
        m_colorMatcher.addColorMatch(RedTarget);
        m_colorMatcher.addColorMatch(YellowTarget);
    }

    @Override
    public void periodic() {
        Color detectedColor = m_colorSensor.getColor();

        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == BlueTarget) {
          colorString = "BLUE";
        } else if (match.color == RedTarget) {
          colorString = "RED";
        } else if (match.color == GreenTarget) {
          colorString = "GREEN";
        } else if (match.color == YellowTarget) {
          colorString = "YELLOW";
        } else {
          colorString = "UNKOWN";
        }

        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
    }

    public String getColor() {
        return colorString;
    }
}
