package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;


public class DrivetrainSubsystems extends SubsystemBase {
    private final CANSparkMax frontRight = new CANSparkMax(6, MotorType.kBrushless);
    private final CANSparkMax backRight = new CANSparkMax(1, MotorType.kBrushless);
    private final CANSparkMax frontLeft = new CANSparkMax(5, MotorType.kBrushless);
    private final CANSparkMax backLeft = new CANSparkMax(2, MotorType.kBrushless);

    private final CANEncoder m_backRightEncoder = backRight.getEncoder();
    private final CANEncoder m_frontRightEncoder = frontRight.getEncoder();

    private SpeedControllerGroup m_right = new SpeedControllerGroup(frontRight, backRight);
    private SpeedControllerGroup m_left = new SpeedControllerGroup(frontLeft, backLeft);
    private SpeedControllerGroup m_right_invert = new SpeedControllerGroup(frontRight, backRight);
    private SpeedControllerGroup m_left_invert = new SpeedControllerGroup(frontLeft, backLeft);

    private DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
    private DifferentialDrive m_drive_inverted = new DifferentialDrive(m_left_invert, m_right_invert);

    private final double m_drive_mod = .75;

    public DrivetrainSubsystems(){
        frontLeft.restoreFactoryDefaults();
        backLeft.restoreFactoryDefaults();
        frontRight.restoreFactoryDefaults();
        backRight.restoreFactoryDefaults();

        frontRight.setMotorType(MotorType.kBrushless);
        backRight.setMotorType(MotorType.kBrushless);
        frontLeft.setMotorType(MotorType.kBrushless);
        backLeft.setMotorType(MotorType.kBrushless);

        frontRight.setIdleMode(IdleMode.kBrake);
        backRight.setIdleMode(IdleMode.kBrake);
        frontLeft.setIdleMode(IdleMode.kBrake);
        backLeft.setIdleMode(IdleMode.kBrake);

        frontRight.setSmartCurrentLimit(40);
        backRight.setSmartCurrentLimit(40);
        frontLeft.setSmartCurrentLimit(40);
        backLeft.setSmartCurrentLimit(40);
    }

    public void tank_drive(double left, double right){
        m_drive.tankDrive(left*m_drive_mod, right*m_drive_mod);
    }

    public void tank_drive_inverted(double left, double right){
        m_drive_inverted.tankDrive(left*-m_drive_mod, right*-m_drive_mod);
    }

    @Override
    public void periodic(){

    }
}
