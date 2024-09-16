// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import java.util.function.Supplier;

import frc.robot.Constants.DriveConstants;

import com.revrobotics.CANSparkMax;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;



public class DrivebaseS extends SubsystemBase {



  private CANSparkMax motorFL = new CANSparkMax(5, MotorType.kBrushless);
  private CANSparkMax motorBR = new CANSparkMax(4, MotorType.kBrushless);
  private CANSparkMax motorBL = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax motorFR = new CANSparkMax(2, MotorType.kBrushless);

private MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
  new Translation2d(
    Units.inchesToMeters(7.5),
    Units.inchesToMeters(6.5)
  ),
  new Translation2d(
    Units.inchesToMeters(7.5),
    Units.inchesToMeters(-6.5)
  ),
  new Translation2d(
    Units.inchesToMeters(-7.5),
    Units.inchesToMeters(6.5)
  ),
  new Translation2d(
    Units.inchesToMeters(-7.5),
    Units.inchesToMeters(-6.5)));

  /** Creates a new ExampleSubsystem. */
  public DrivebaseS() {
    motorBL.setSmartCurrentLimit(DriveConstants.CURRENT_LIMIT);
    motorFL.setSmartCurrentLimit(DriveConstants.CURRENT_LIMIT);
    motorBR.setSmartCurrentLimit(DriveConstants.CURRENT_LIMIT);
    motorBR.setSmartCurrentLimit(DriveConstants.CURRENT_LIMIT);

  }


  public void drive (ChassisSpeeds Speeds) {
    MecanumDriveWheelSpeeds wheels = kinematics.toWheelSpeeds(Speeds);
    motorFL.setVoltage(DriveConstants.K_V * wheels.frontLeftMetersPerSecond);
    motorFR.setVoltage(DriveConstants.K_V * wheels.frontRightMetersPerSecond);
    motorBL.setVoltage(DriveConstants.K_V * wheels.rearLeftMetersPerSecond);
    motorBR.setVoltage(DriveConstants.K_V * wheels.rearRightMetersPerSecond);
  }

  public Command driveC (Supplier<ChassisSpeeds> Speeds) {
    return run(()->drive(Speeds.get()));
  }
}




 
  
