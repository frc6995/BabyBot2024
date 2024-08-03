// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import java.util.function.Supplier;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
  public DrivebaseS() {}


  public void drive (ChassisSpeeds Speeds) {
    MecanumDriveWheelSpeeds wheels = kinematics.toWheelSpeeds(Speeds);
    motorFL.setVoltage(4* wheels.frontLeftMetersPerSecond);
    motorFR.setVoltage(4* wheels.frontRightMetersPerSecond);
    motorBL.setVoltage(4* wheels.rearLeftMetersPerSecond);
    motorBR.setVoltage(4* wheels.rearRightMetersPerSecond);
  }

  public Command driveC (Supplier<ChassisSpeeds> Speeds) {
    return run(()->drive(Speeds.get()));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
