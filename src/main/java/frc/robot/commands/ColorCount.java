package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;
import edu.wpi.first.wpilibj.util.Color;

/**
 * An example command that uses an example subsystem.
 */
public class ColorCount extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ColorSensor colorSensor;
  private final ControlPanelSubsystem controlPanel;

  private static int changeCount = 0;
  private static int rotationCount = 0;
  private static Color previousColor = new Color(0, 0, 0);
  private boolean useColor = true;

  public ColorCount(ColorSensor c, ControlPanelSubsystem p, boolean u) {
    colorSensor = c;
    controlPanel = p;
    this.useColor = u;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(colorSensor);
    addRequirements(controlPanel);
  }

  @Override
  public void initialize() {
      // TODO Auto-generated method stub
      super.initialize();
  }
 
  @Override
  public boolean isFinished() {
      // TODO Auto-generated method stub
      return super.isFinished();
  }

  @Override
  public void execute() {
    if (useColor) {
      boolean spin = true;
      controlPanel.controlPanelMotor.set(10);
      while(spin) {
          spin = shouldContinueColor(colorSensor.getSensorColor());
      }
      controlPanel.controlPanelMotor.stopMotor();
    } else {
      spinRPM();
    }
    setColor(colorSensor.getSensorColor(), colorSensor.getFieldColor());
  }

  private boolean shouldContinueColor(Color detected) {
    if(!colorSensor.isSameColor(detected, previousColor)) { 
        changeCount += 1;
        if(changeCount >= 7) {
            changeCount = 0;
            rotationCount += 1;
           return true;
        }
        previousColor = detected;
    } else if (colorSensor.isSameColor(detected, previousColor) && rotationCount < 3) {
      return true;
    } else if (rotationCount > 3) {
      return false;
    }
    return false;
  }

  private void spinRPM() {
    double rotations = controlPanel.controlPanelMotorEncoder.getPosition();
    controlPanel.controlPanelMotor.set(10);
    while (rotations < 32.0f) {
      // (p)ass
    }
    controlPanel.controlPanelMotor.stopMotor();
  }

  private void setColor(Color detected, Color given) {
    Color actual = ColorSensor.colors.get(colorSensor.getColorIndex(detected) - 2); 
    // color we want is 2 ahead, so we need to go before
    controlPanel.controlPanelMotor.set(10);
    while (!colorSensor.isSameColor(detected, actual)) {
      // (p)ass
    }
    controlPanel.controlPanelMotor.stopMotor();
  }
}