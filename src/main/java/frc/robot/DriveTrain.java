/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import javax.sound.midi.spi.MidiFileReader;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.CANSparkMax.IdleMode;

/**
 * Add your docs here.
 */
public class DriveTrain {
    private CANSparkMax frontRight, middleRight, backRight, frontLeft, middleLeft, backLeft;
    private XboxController controller;

    private double speedMod = 1;
    private double speedMod2 = 1;
    private double speedMod3 = .5;
    private boolean POVPressed = false;

    public DriveTrain(CANSparkMax frontRight, CANSparkMax backRight, CANSparkMax middleRight, CANSparkMax middleLeft,
            CANSparkMax frontLeft, CANSparkMax backLeft, XboxController controller) {

        this.frontRight = frontRight;
        this.middleRight = middleRight;
        this.backRight = backRight;
        this.frontLeft = frontLeft;
        this.middleLeft = middleLeft;
        this.backLeft = backLeft;

        frontLeft.setMotorType(MotorType.kBrushless);
        middleLeft.setMotorType(MotorType.kBrushless);
        backLeft.setMotorType(MotorType.kBrushless);
        frontRight.setMotorType(MotorType.kBrushless);
        middleRight.setMotorType(MotorType.kBrushless);
        backRight.setMotorType(MotorType.kBrushless);
    }

    public void baseDrive() {
        if (controller.getTriggerAxis(GenericHID.Hand.kRight) > .7 * .9) {
            speedMod3 = controller.getTriggerAxis(GenericHID.Hand.kRight) * .9;
        } else {
            speedMod3 = .5;
        }

        if (Math.abs(controller.getY(Hand.kLeft)) > .2 || Math.abs(controller.getY(Hand.kRight)) > .2) {
            frontLeft.set(-controller.getY(GenericHID.Hand.kLeft) * speedMod * speedMod2 * speedMod3);
            middleLeft.set(-controller.getY(GenericHID.Hand.kLeft) * speedMod * speedMod2 * speedMod3);
            backLeft.set(-controller.getY(GenericHID.Hand.kLeft) * speedMod * speedMod2 * speedMod3);
            frontRight.set(-controller.getY(GenericHID.Hand.kRight) * speedMod * speedMod2 * speedMod3);
            middleRight.set(-controller.getY(GenericHID.Hand.kLeft) * speedMod * speedMod2 * speedMod3);
            backRight.set(-controller.getY(GenericHID.Hand.kRight) * speedMod * speedMod2 * speedMod3);
        } else {
            frontLeft.set(0);
            middleLeft.set(0);
            backLeft.set(0);
            frontRight.set(0);
            middleRight.set(0);
            backRight.set(0);
        }
        if (controller.getXButtonPressed()) {
            frontRight.setIdleMode(IdleMode.kCoast);
            frontLeft.setIdleMode(IdleMode.kCoast);
            middleRight.setIdleMode(IdleMode.kCoast);
            middleLeft.setIdleMode(IdleMode.kCoast);
            backRight.setIdleMode(IdleMode.kCoast);
            backLeft.setIdleMode(IdleMode.kCoast);

        }
        if (controller.getBButtonPressed()) {
            frontRight.setIdleMode(IdleMode.kBrake);
            frontLeft.setIdleMode(IdleMode.kBrake);
            middleRight.setIdleMode(IdleMode.kBrake);
            middleLeft.setIdleMode(IdleMode.kBrake);
            backLeft.setIdleMode(IdleMode.kBrake);
            backRight.setIdleMode(IdleMode.kBrake);
        }
        // System.out.println("Front Right: " + frontRight.get());
        // System.out.println("Back Right: " + backRight.get());
        // System.out.println("Front Left: " + frontLeft.get());
        // System.out.println("BackLeft: " + backLeft.get());
        speedModAdjust();
    }

    public void brakeSystemAdjust() {
        if (controller.getXButtonPressed()) {
            frontLeft.setIdleMode(IdleMode.kCoast);
            frontRight.setIdleMode(IdleMode.kCoast);
            middleRight.setIdleMode(IdleMode.kCoast);
            middleLeft.setIdleMode(IdleMode.kCoast);
            backLeft.setIdleMode(IdleMode.kCoast);
            backRight.setIdleMode(IdleMode.kCoast);
        } else if (controller.getBButtonPressed()) {
            frontLeft.setIdleMode(IdleMode.kBrake);
            frontRight.setIdleMode(IdleMode.kBrake);
            middleRight.setIdleMode(IdleMode.kBrake);
            middleLeft.setIdleMode(IdleMode.kBrake);
            backLeft.setIdleMode(IdleMode.kBrake);
            backRight.setIdleMode(IdleMode.kBrake);
        }
    }

    public void speedModAdjust() {
        if (!POVPressed && controller.getPOV(0) > -1) {
            if (controller.getPOV(0) == 0 && speedMod < .99) {
                speedMod += .1;
                POVPressed = true;
            } else if (controller.getPOV(0) == 180 && speedMod > .01) {
                speedMod -= .1;
                POVPressed = true;
            }

        } else if (POVPressed && controller.getPOV(0) == -1) {
            POVPressed = false;
        }
        // System.out.println(speedMod);
    }

    public void setSpeedMod2(double speedMod) {
        this.speedMod2 = speedMod;
    }

    public void displayValues(){
      SmartDashboard.putNumber("Front Right", frontRight.get());
      SmartDashboard.putNumber("Middle Right", middleRight.get());
      SmartDashboard.putNumber("Back Right", backRight.get());
      SmartDashboard.putNumber("Front Left", frontLeft.get());
      SmartDashboard.putNumber("Middle Left", middleLeft.get());
      SmartDashboard.putNumber("Back Left", backLeft.get());
    }

}
