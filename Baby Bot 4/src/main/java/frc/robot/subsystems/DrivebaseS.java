// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import java.util.function.Supplier;

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

  public int maxBoosts = 1;
  public int timesBoosted = 0;
  public int boostTimeSeconds = 10;

  private CANSparkMax motorFL = new CANSparkMax(5, MotorType.kBrushless);
  private CANSparkMax motorBR = new CANSparkMax(4, MotorType.kBrushless);
  private CANSparkMax motorBL = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax motorFR = new CANSparkMax(2, MotorType.kBrushless);

  private double slowSpeed = 3.2;
  private double fastSpeed = 6.3;

  private double SpeedMultiplier = slowSpeed;


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
    motorFL.setVoltage(SpeedMultiplier * wheels.frontLeftMetersPerSecond);
    motorFR.setVoltage(SpeedMultiplier * wheels.frontRightMetersPerSecond);
    motorBL.setVoltage(SpeedMultiplier * wheels.rearLeftMetersPerSecond);
    motorBR.setVoltage(SpeedMultiplier * wheels.rearRightMetersPerSecond);
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
  public final Trigger trg_canBoost = new Trigger(()->(SpeedMultiplier != fastSpeed && timesBoosted < maxBoosts));
 public Command ToggleFastC() {
    return Commands.sequence(
      Commands.runOnce(()-> {
        SpeedMultiplier = fastSpeed;
 }),
    Commands.waitSeconds(boostTimeSeconds),
    Commands.runOnce(()-> {
      ++timesBoosted;
      SpeedMultiplier = slowSpeed;})
    );
      
  }

  public void ToggleSpeed() {
      if (SpeedMultiplier == slowSpeed){
        SpeedMultiplier = fastSpeed;
      }
      else{
        SpeedMultiplier = slowSpeed;
      }
  }


  public void ResetBoost() {
    timesBoosted = 0;
  }

  public Command ResetBoostC () {
    return runOnce(this::ResetBoost);
  }

}




 
  
