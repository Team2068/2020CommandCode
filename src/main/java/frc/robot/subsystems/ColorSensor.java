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
import java.util.ArrayList;

public class ColorSensor extends SubsystemBase {

  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color NullTarget = ColorMatch.makeColor(0.0, 0.0, 0.0);
  private final Color BlueTarget = ColorMatch.makeColor(0.0, 0.5, 1.0); // 0.143, 0.427, 0.429
  private final Color GreenTarget = ColorMatch.makeColor(0.0, 1.0, 0.0); // 0.197, 0.561, 0.240
  private final Color RedTarget = ColorMatch.makeColor(0.75, 0.15, 0.0); // 0.561, 0.232, 0.144
  private final Color YellowTarget = ColorMatch.makeColor(0.8, 0.45, 0); // 0.361, 0.524, 0.113
  private Color givenColor;

  public static ArrayList<Color> colors = new ArrayList<Color>();

  private String colorString;
  private SendableChooser<Color> colorChooser = new SendableChooser<>();
  private ShuffleboardTab dashboardTab = Shuffleboard.getTab("SmartDashboard");
  private Color detectedColor = ColorMatch.makeColor(0.0, 0.0, 0.0);

  private Color getColorMatch() {
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
      case 'B':
        return BlueTarget;
      case 'G':
        return GreenTarget;
      case 'R':
        return RedTarget;
      case 'Y':
        return YellowTarget;
      default:
        DriverStation.reportWarning("Corrupt Color Data Recieved from FMS", false);
        break;
      }
    } else {
      DriverStation.reportWarning("No Color Data Receieved from FMS", false);
    }
    return NullTarget;
  }

  private void createColorChooser() {
    SendableRegistry.add(colorChooser, "Color Chooser");
    SendableRegistry.setName(colorChooser, "Color Chooser");

    colorChooser.setDefaultOption("None", NullTarget);
    colorChooser.addOption("Blue", BlueTarget);
    colorChooser.addOption("Green", GreenTarget);
    colorChooser.addOption("Red", RedTarget);
    colorChooser.addOption("Yellow", YellowTarget);

    dashboardTab.add(colorChooser).withWidget(BuiltInWidgets.kComboBoxChooser).withSize(1, 1);
  }

  public ColorSensor() {
    m_colorMatcher.addColorMatch(BlueTarget);
    m_colorMatcher.addColorMatch(GreenTarget);
    m_colorMatcher.addColorMatch(RedTarget);
    m_colorMatcher.addColorMatch(YellowTarget);
    colors.add(0, RedTarget);
    colors.add(1, GreenTarget);
    colors.add(2, BlueTarget);
    colors.add(3, YellowTarget);
    createColorChooser();
  }

  @Override
  public void periodic() {
    detectedColor = m_colorSensor.getColor();
    correctRed(detectedColor);
    if (DriverStation.getInstance().isFMSAttached()) {
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
      colorString = "UNKNOWN";
    }

    SmartDashboard.putString("Detected Color", colorString);
    Dashboard.putDebugNumber("Confidence", match.confidence);
    Dashboard.putDebugNumber("Detected Red", match.color.red);
    Dashboard.putDebugNumber("Detected Green", match.color.green);
    Dashboard.putDebugNumber("Detected Blue", match.color.blue);
  }

  public Color getSensorColor() {
    return detectedColor;
  }

  public Color getFieldColor() {
    return givenColor;
  }

  public boolean isSameColor(Color c1, Color c2) {
    ColorMatchResult match = m_colorMatcher.matchClosestColor(c1);
    if (match.color == c2) {
      return true;
    }
    return false;
  }

  public int getColorIndex(Color c) {
    for (int i = 0; i < colors.size(); i++) {
      if (isSameColor(c, colors.get(i)))
        return i;
    }
    return 0;
  }

  private void correctRed(Color c) {
    ColorMatchResult m = m_colorMatcher.matchClosestColor(c);
    if (m.color == GreenTarget || m.color == BlueTarget) {
      return;
    }
    if (c.red > c.green - .1) {
      detectedColor = RedTarget;
    } else {
      detectedColor = YellowTarget;
    }
  }
}
