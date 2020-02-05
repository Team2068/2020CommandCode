package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Limelight extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */

  public Limelight() {

  }
  public Limelight(final int c, final int s){
      set_mode(c);
      set_stream(s);
  }


  @Override
  public void periodic() {
    final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    final NetworkTableEntry tx = table.getEntry("tx");
    final NetworkTableEntry ty = table.getEntry("ty");
    final NetworkTableEntry ta = table.getEntry("ta");

    //read values periodically
    final double x = tx.getDouble(0.0);
    final double y = ty.getDouble(0.0);
    final double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
  }

  public void set_mode(final int m){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(m);
  }
  public void set_stream(final int m){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(m);
  }

}

