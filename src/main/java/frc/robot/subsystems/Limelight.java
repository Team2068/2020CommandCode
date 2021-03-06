package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

public class Limelight extends SubsystemBase {

    public Limelight(int camMode, int streamMode) {
        setMode(camMode);
        setStream(streamMode);
    }

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
        final String stream, cam;

        if (getStream() == Constants.StreamMode.PIP_MAIN) {
            stream = "Main";
        } else {
            stream = "Secondary";
        }

        if (getMode() == Constants.CameraMode.VISION) {
            cam = "Vision";
            table.getEntry("ledMode").setNumber(3);
        } else {
            cam = "Driver";
            table.getEntry("ledMode").setNumber(0);
        }

        // Post to smart dashboard periodically
        Dashboard.putDebugNumber("LimelightX", x);
        Dashboard.putDebugNumber("LimelightY", y);
        Dashboard.putDebugNumber("LimelightArea", area);
        SmartDashboard.putString("Stream Mode", stream);
        SmartDashboard.putString("Camera Mode", cam);
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
