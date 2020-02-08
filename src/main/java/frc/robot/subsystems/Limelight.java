package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {

  @Override
  public void periodic() {
    final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    final NetworkTableEntry tx = table.getEntry("tx");
    final NetworkTableEntry ty = table.getEntry("ty");
    final NetworkTableEntry ta = table.getEntry("ta");

    // read values periodically
    final double x = tx.getDouble(0.0);
    final double y = ty.getDouble(0.0);
    final double area = ta.getDouble(0.0);

    // Post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
  }

  public void setMode(final int m) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(m);
  }

  public void setStream(final int m) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(m);
  }

  public int getStream() {
    return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").getDouble(0.0);
  }

  public int getMode() {
    return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getDouble(0.0);
  }
}