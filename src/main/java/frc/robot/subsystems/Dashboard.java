package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Dashboard extends SubsystemBase {

    private static ArrayList<String> keys = new ArrayList<String>();

    public Dashboard() {
        if (!Constants.debug) {
            destroyAllObjects();
        }
    }

    public static void putDebugString(String key, String value) {
        if (Constants.debug) {
            SmartDashboard.putString(key, value);
            keys.add(key);
        }
    }

    public static void putDebugNumber(String key, double value) {
        if (Constants.debug) {
            SmartDashboard.putNumber(key, value);
            keys.add(key);
        }
    }

    public static void putDebugData(String key, Sendable data) {
        if (Constants.debug) {
            SmartDashboard.putData(key, data);
            keys.add(key);
        }
    }

    private static void destroyAllObjects() {
        for (int i = 0; i < keys.size(); i++) {
            SmartDashboard.delete(keys.get(i));
        }
    }
}