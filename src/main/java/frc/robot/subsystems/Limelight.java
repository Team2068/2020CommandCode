package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class Limelight extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */

  public Limelight() {

  }
  public Limelight(int c, int s){
      set_mode(c);
      set_stream(s);
  }

  @Override
  public void periodic() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
  }

  public void set_mode(int m){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(m);
  }
  public void set_stream(int m){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(m);
  }

}

