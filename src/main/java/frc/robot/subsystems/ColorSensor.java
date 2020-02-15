/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorSensor extends SubsystemBase {
  
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();

    private final Color NullTarget = ColorMatch.makeColor(0.0, 0.0, 0.0);
    private final Color BlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color GreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color RedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color YellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private String colorString;
    private SendableChooser<Color> colorChooser = new SendableChooser<>();
    private ShuffleboardTab dashboardTab = Shuffleboard.getTab("SmartDashboard");
    private Color detectedColor = ColorMatch.makeColor(0.0, 0.0, 0.0);

    private Color getColorMatch(){
      String gameData = DriverStation.getInstance().getGameSpecificMessage();
      if(gameData.length() > 0)
      {
        switch (gameData.charAt(0))
        {
          case 'B' : return BlueTarget;
          case 'G' : return GreenTarget;
          case 'R' : return RedTarget;
          case 'Y' : return YellowTarget;
          default :
            DriverStation.reportWarning("Corrupt Color Data Recieved from FMS", false);
            break;
        }
      } else {
        DriverStation.reportWarning("No Color Data Receieved from FMS", false);
      }
      return NullTarget;
    }

    private void createColorChooser(){
      SendableRegistry.add(colorChooser, "Color Chooser");
      SendableRegistry.setName(colorChooser, "Color Chooser");

      colorChooser.setDefaultOption("None", NullTarget);   
      colorChooser.addOption("Blue", BlueTarget);
      colorChooser.addOption("Green", GreenTarget);
      colorChooser.addOption("Red", RedTarget);
      colorChooser.addOption("Yellow", YellowTarget);
      
      dashboardTab.add(colorChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser)
        .withSize(1,1);
    }

    public ColorSensor() {
        m_colorMatcher.addColorMatch(BlueTarget);
        m_colorMatcher.addColorMatch(GreenTarget);
        m_colorMatcher.addColorMatch(RedTarget);
        m_colorMatcher.addColorMatch(YellowTarget);
        createColorChooser();
    }

    @Override
    public void periodic() {
        detectedColor = m_colorSensor.getColor();
        Color givenColor;
        if(DriverStation.getInstance().isFMSAttached()){
          givenColor = getColorMatch();
        } else {
          givenColor = colorChooser.getSelected();
        }
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

    public Color getSensorColor() {
      return detectedColor;
    }

    public boolean isSameColor(Color c1, Color c2) {
      ColorMatchResult match = m_colorMatcher.matchClosestColor(c1);
      if(match.color == c2) {
        return true; 
      } 
      return false;
    }
}
