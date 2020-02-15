package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanelSubsystem;
import edu.wpi.first.wpilibj.util.Color;

/**
 * An example command that uses an example subsystem.
 */
public class ColorCount extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final ColorSensor colorSensor;
  private final ControlPanelSubsystem controlPanel;

  private static int changeCount = 0;
  private static int rotationCount = 0;
  private boolean useColor = true;
  private static Color targetColor; // Pull actual targets from FMS and shuffleboard
  private static Color previousColor;
  private static int targetRotation = 4;

  public ColorCount(ColorSensor c, ControlPanelSubsystem p, boolean b) {
    colorSensor = c;
    controlPanel = p;
    useColor = b;

    targetColor = colorSensor.getFieldColor();
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
      while (spin) {
        spin = shouldContinueColor(colorSensor.getSensorColor());
      }
      controlPanel.controlPanelMotor.stopMotor();
    } else {
      spinRPM();
    }
    setColor(colorSensor.getSensorColor(), targetColor);
  }

  private boolean shouldContinueColor(Color detected) {
    boolean sameColor = colorSensor.isSameColor(detected, previousColor);
    if (!sameColor) { // Color is changed
      changeCount += 1; // Add 1 to change counter
      if (changeCount >= 7) { // If 1 rotation done ( 7 changes ) add 1 to rotation
        changeCount = 0;
        rotationCount += 1;
      }
      previousColor = detected;
    }

    if (rotationCount >= targetRotation && detected == targetColor) { // If reached target color and target rotation
      return false; // Stop spinning
    }
    return true; // Continue Spinning
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