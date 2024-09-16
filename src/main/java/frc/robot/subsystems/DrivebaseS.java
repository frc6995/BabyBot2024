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
import static frc.robot.subsystems.DrivebaseS.DriveConstants.*;

public class DrivebaseS extends SubsystemBase {

  public static class DriveConstants {
    public static final int CURRENT_LIMIT = 50;
    public static final double K_V = 2.0; // V/(m/s)
    public static final double HALF_LENGTH = Units.inchesToMeters(7.5);
    public static final double HALF_WIDTH = Units.inchesToMeters(6.5);
    public static final int CAN_ID_FL = 5;
    public static final int CAN_ID_BR = 4;
    public static final int CAN_ID_BL = 3;
    public static final int CAN_ID_FR = 2;
  }
  private CANSparkMax motorFL = new CANSparkMax(CAN_ID_FL, MotorType.kBrushless);
  private CANSparkMax motorBR = new CANSparkMax(CAN_ID_BR, MotorType.kBrushless);
  private CANSparkMax motorBL = new CANSparkMax(CAN_ID_BL, MotorType.kBrushless);
  private CANSparkMax motorFR = new CANSparkMax(CAN_ID_FR, MotorType.kBrushless);

  private MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
      new Translation2d(+HALF_LENGTH, +HALF_WIDTH),
      new Translation2d(+HALF_LENGTH, -HALF_WIDTH),
      new Translation2d(-HALF_LENGTH, +HALF_WIDTH),
      new Translation2d(-HALF_LENGTH, -HALF_WIDTH)
  );

  /** Creates a new ExampleSubsystem. */
  public DrivebaseS() {
    motorBL.setSmartCurrentLimit(CURRENT_LIMIT);
    motorFL.setSmartCurrentLimit(CURRENT_LIMIT);
    motorBR.setSmartCurrentLimit(CURRENT_LIMIT);
    motorBR.setSmartCurrentLimit(CURRENT_LIMIT);

  }

  public void drive(ChassisSpeeds Speeds) {
    MecanumDriveWheelSpeeds wheels = kinematics.toWheelSpeeds(Speeds);
    motorFL.setVoltage(K_V * wheels.frontLeftMetersPerSecond);
    motorFR.setVoltage(K_V * wheels.frontRightMetersPerSecond);
    motorBL.setVoltage(K_V * wheels.rearLeftMetersPerSecond);
    motorBR.setVoltage(K_V * wheels.rearRightMetersPerSecond);
  }

  public Command driveC(Supplier<ChassisSpeeds> Speeds) {
    return run(() -> drive(Speeds.get()));
  }
}
