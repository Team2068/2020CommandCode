package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

public class IntakeSubsystem extends SubsystemBase {

    private final CANSparkMax m_intake = new CANSparkMax(0, MotorType.kBrushless);
    private final CANSparkMax m_leftConveyor = new CANSparkMax(0, MotorType.kBrushless);
    private final CANSparkMax m_rightConveyor = new CANSparkMax(0, MotorType.kBrushless);

    public IntakeSubsystem(){
        m_intake.restoreFactoryDefaults();
        m_leftConveyor.restoreFactoryDefaults();
        m_rightConveyor.restoreFactoryDefaults();

        m_intake.setIdleMode(IdleMode.kBrake);
        m_leftConveyor.setIdleMode(IdleMode.kBrake);
        m_rightConveyor.setIdleMode(IdleMode.kBrake);
        
        m_intake.setSmartCurrentLimit(40);
        m_leftConveyor.setSmartCurrentLimit(40);
        m_rightConveyor.setSmartCurrentLimit(40);
    }

    @Override
    public void periodic(){
    }
}