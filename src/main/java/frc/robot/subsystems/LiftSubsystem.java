package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

public class LiftSubsystem extends SubsystemBase {

    private final CANSparkMax m_rightHook = new CANSparkMax(0, MotorType.kBrushless);
    private final CANSparkMax m_leftHook = new CANSparkMax(0, MotorType.kBrushless);
    private final CANSparkMax m_rightRatchet = new CANSparkMax(0, MotorType.kBrushless);
    private final CANSparkMax m_leftRatchet = new CANSparkMax(0, MotorType.kBrushless);

    public LiftSubsystem(){
        m_rightHook.restoreFactoryDefaults();
        m_leftHook.restoreFactoryDefaults();
        m_rightRatchet.restoreFactoryDefaults();
        m_leftRatchet.restoreFactoryDefaults();

        m_rightHook.setIdleMode(IdleMode.kBrake);
        m_leftHook.setIdleMode(IdleMode.kBrake);
        m_rightRatchet.setIdleMode(IdleMode.kBrake);
        m_leftRatchet.setIdleMode(IdleMode.kBrake);

        m_rightHook.setSmartCurrentLimit(40);
        m_leftHook.setSmartCurrentLimit(40);
        m_rightRatchet.setSmartCurrentLimit(40);
        m_leftRatchet.setSmartCurrentLimit(40);
    }

    @Override
    public void periodic(){
    }
}