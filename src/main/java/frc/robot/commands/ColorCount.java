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
        spin = shouldContinue(colorSensor.getSensorColor());
        // Spin motor
    }
  }

  private boolean shouldContinue(Color detected) {
    if(colorSensor.isSameColor(detected, previousColor)) {
        changeCount += 1;
        if(changeCount >= 12) {
            changeCount = 0;
            rotationCount += 1;
            return true;
        }
        previousColor = detected;
    }
    return false;
  }
}