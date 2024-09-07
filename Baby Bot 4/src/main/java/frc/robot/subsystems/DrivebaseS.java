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



public class DrivebaseS extends SubsystemBase {

  public boolean isBoosting = false;

  private CANSparkMax motorFL = new CANSparkMax(5, MotorType.kBrushless);
  private CANSparkMax motorBR = new CANSparkMax(4, MotorType.kBrushless);
  private CANSparkMax motorBL = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax motorFR = new CANSparkMax(2, MotorType.kBrushless);

  private double slowSpeed = 3;
  private double fastSpeed = 6;

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
    motorFL.setVoltage(SpeedMultiplier* wheels.frontLeftMetersPerSecond);
    motorFR.setVoltage(SpeedMultiplier* wheels.frontRightMetersPerSecond);
    motorBL.setVoltage(SpeedMultiplier* wheels.rearLeftMetersPerSecond);
    motorBR.setVoltage(SpeedMultiplier* wheels.rearRightMetersPerSecond);
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
 public Command ToggleFastC() {
      //if (!isBoosting) {
        return Commands.sequence(Commands.runOnce(this::ToggleSpeed),
        Commands.runOnce(this::ToggleSpeed),
        Commands.runOnce(()-> isBoosting = true)).onlyIf(()->isBoosting);
      //}
      //else {
        //return Commands.runOnce(()-> isBoosting = false);
      //}

      /*return Commands.sequence(Commands.sequence(Commands.runOnce(this::ToggleSpeed),
        Commands.runOnce(this::ToggleSpeed),
        Commands.runOnce(()-> isBoosting = true)).onlyIf(()->boosttimer.canBoost), 
        );*/
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ResetBoost'");
  }

}




 
  
