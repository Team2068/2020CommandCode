/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    private CANSparkMax wheelSpinMotor = new CANSparkMax(0, MotorType.kBrushless);

    private CANSparkMax leftConveyor = new CANSparkMax(0, MotorType.kBrushless);
    private CANSparkMax rightConveyor = new CANSparkMax(0, MotorType.kBrushless);


    private XboxController mechanismController = new XboxController(2);
    private Joystick rightJoystick = new Joystick(0);
    private Joystick leftJoystick = new Joystick(1);  

    public static enum CameraMode {
        VISION, DRIVER
    }
    
    public static enum StreamMode {
        STANDARD, PIP_MAIN, PIP_SECONDARY
    }

}
