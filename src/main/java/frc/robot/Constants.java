/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int CURRENT_LIMIT = 30;
    public static boolean debug = true;

    public final static class DriveConstants {

        public static final int FRONT_LEFT = 1;
        public static final int BACK_LEFT = 2;
        public static final int FRONT_RIGHT = 3;
        public static final int BACK_RIGHT = 4;
        public static final double NORMAL_SPEED = 0.5;
        public static final int driverController = 0;
        public static final int mechanismController = 1;
        public static final double TURBO_SPEED = 1;
        public static final double SLOW_SPEED = 0.25;

    }

    public final static class ControllerConstants {
        public static final int RIGHT_TRIGGER = 3;
        public static final int LEFT_TRIGGER = 2;
        public static final double TRIGGER_ACTIVATION_THRESHOLD = .3;
        public static final int POV_ANGLE_UP = 0;
        public static final int POV_ANGLE_LEFT = 270;
        public static final int POV_ANGLE_RIGHT = 90;
    }

    public final static class HangConstants {

        public static final int LIFT_MOTOR = 8;
        public static final int WINCH_MOTOR = 7;
        public static final int LIFT_ENCODER_VALUE = 262;
        public static final int BOTTOM_LIFT_ENCODER = 225;
        public static final double LIFT_SPEED = 1;
        public static final double WINCH_MOTOR_SPEED = 1;
    }

    public final static class LowScoringConstants {

        public static final int CONVEYOR_MOTOR = 6; // for transporting balls
        public static final int ROLLER_MOTOR = 9; // the spinning thingy to pick up POWERCELLS or reject them
        public static final double CONVEYOR_SPEED = .5;
        public static final double ROLLER_SPEED = .3;

    }

    public final static class ControlPanelConstants {
        public static final int FORWARD_CHANNEL = 1;
        public static final int REVERSE_CHANNEL = 0;
        public static final int CONTROL_PANEL_MOTOR = 5;
        public static final int CONTROL_PANEL_LIMIT = 30;
        public static final double MOTOR_SPEED = 0.75;
    }

    public final static class LowPressureConstants {
        public static final int PRESSURE_SENSOR_CHANNEL = 0;
    }

    public static class CameraMode {
        public static final int VISION = 0, DRIVER = 1;
    }

    public static class StreamMode {
        public static final int STANDARD = 0, PIP_MAIN = 1, PIP_SECONDARY = 2;
    }
}
