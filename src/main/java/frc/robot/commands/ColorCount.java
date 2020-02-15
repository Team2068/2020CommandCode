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

  private enum TargetColors {
    Red, Green, Blue, Yellow;
  }

  public ColorCount(ColorSensor c, ControlPanelSubsystem p) {
    colorSensor = c;
    controlPanel = p;
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
    boolean spin = true;
    while(spin) {
        spin = shouldContinueColor(colorSensor.getSensorColor());
        // Spin motor
    }
    // spinRPM(); spin using rpm not color
  }

  private boolean shouldContinueColor(Color detected) {
    if(!colorSensor.isSameColor(detected, previousColor)) { // color is changed
        changeCount += 1; // add 1 to change counter
        if(changeCount >= 7) { // if 1 rotation done ( 7 changes ) add 1 to rotation
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
    int cpr = controlPanel.controlPanelMotorEncoder.getCountsPerRevolution();
    while (cpr < 32) {
      controlPanel.controlPanelMotor.set(10);
    }
    controlPanel.controlPanelMotor.stopMotor();
  }
}