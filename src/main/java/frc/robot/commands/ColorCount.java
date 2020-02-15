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

  private static Color targetColor = new Color(0, 0, 0);  //Pull actual targets from FMS and shuffleboard
  private static int targetRotation = 4;                  // ^

  //private enum TargetColors {     <= Why is this necessary?
  //  Red, Green, Blue, Yellow;
  //}

  public ColorCount(ColorSensor c, ControlPanelSubsystem p) {
    colorSensor = c;
    controlPanel = p;

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
    while(spin) { //Switch this to delta time??
        spin = shouldContinueColor(colorSensor.getSensorColor()); //Checks to continue spinning
        // Spin motor
    }
    // spinRPM(); spin using rpm not color
  }

  private boolean shouldContinueColor(Color detected) {
    boolean sameColor = colorSensor.isSameColor(detected, previousColor);
    
    if(!sameColor) { // Color is changed
        changeCount += 1; // Add 1 to change counter
        if(changeCount >= 7) { // If 1 rotation done ( 7 changes ) add 1 to rotation
            changeCount = 0;
            rotationCount += 1;
        }
        previousColor = detected;
    }

    if(rotationCount >= targetRotation && detected == targetColor)  { //If reached target color and target rotation
      return false; //Stop spinning
    }
    return true; //Continue Spinning
  }

  private void spinRPM() {
    int cpr = controlPanel.controlPanelMotorEncoder.getCountsPerRevolution();
    while (cpr < 32) {
      controlPanel.controlPanelMotor.set(10);
    }
    controlPanel.controlPanelMotor.stopMotor();
  }
}