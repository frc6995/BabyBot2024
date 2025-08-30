package frc.robot.subsystems;

//import com.revrobotics.CANSparkLowLevel.MotorType;

import java.util.function.Supplier;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

//import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
//import edu.wpi.first.math.kinematics.Kinematics;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
//import edu.wpi.first.units.Unit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;


//import java.util.function.Supplier;


public class DriveBase extends SubsystemBase{
    public SparkMax motor1 = new SparkMax(1, MotorType.kBrushless);
    private SparkMax motor1F = new SparkMax(2, MotorType.kBrushless);
    private SparkMax motor4 = new SparkMax(4, MotorType.kBrushless);
    private SparkMax motor4F = new SparkMax(3, MotorType.kBrushless);
    //def postions of the motor
    Translation2d m_front_left = new Translation2d(Units.inchesToMeters(7.5), Units.inchesToMeters(6.5));//modifyed
    Translation2d m_front_right = new Translation2d(Units.inchesToMeters(7.5), Units.inchesToMeters(-6.5));
    Translation2d m_bottom_left = new Translation2d(Units.inchesToMeters(-7.5), Units.inchesToMeters(6.5));
    Translation2d m_bottom_right = new Translation2d(Units.inchesToMeters(-7.5), Units.inchesToMeters(-6.5));//bottom right should be: -7.5  -6.5
    MecanumDriveKinematics Kinematics = new MecanumDriveKinematics(m_front_left, m_front_right, m_bottom_left, m_bottom_right);
    public int timesBoosted = 1;
    private double fastSpeed = 8;
    private double slowSpeed = 4;
    private double m_Speed = slowSpeed;
    public int maxBoosts = 3;
    public int boostTimeSeconds = 3;
    //ChassisSpeeds speeds = new ChassisSpeeds();
    public void dt(ChassisSpeeds Speeds){
        //MecanumDriveWheelSpeeds wheels = Kinematics.towheel
        MecanumDriveWheelSpeeds wheels = Kinematics.toWheelSpeeds(Speeds);
        motor1.setVoltage(-m_Speed*wheels.frontRightMetersPerSecond);
        motor1F.setVoltage(-m_Speed*wheels.rearRightMetersPerSecond);                                                                                                                                       
        motor4.setVoltage(m_Speed*wheels.frontLeftMetersPerSecond);
        motor4F.setVoltage(m_Speed*wheels.rearLeftMetersPerSecond);

        //motor1.setVoltage();
    }

    public Command drivec(Supplier<ChassisSpeeds> Speeds){
        return run(()->dt(Speeds.get()));
    }

    public Command Boost(){
        return run(()->m_Speed = fastSpeed);
    }
    public Command SBoost(){
        return run(()->m_Speed = slowSpeed);
    }
    public Trigger trg_canBoost = new Trigger(()->m_Speed != fastSpeed && timesBoosted < maxBoosts);
    public Command trg_FastC(){
        return Commands.sequence(
      Commands.runOnce(()-> {
        m_Speed = fastSpeed;
    
    }),
    Commands.waitSeconds(boostTimeSeconds),
    Commands.runOnce(()-> {
    ++timesBoosted;
    m_Speed = slowSpeed;})
    );}
    public void ToggleSpeed() {
        if (m_Speed == slowSpeed){
          m_Speed = fastSpeed;
        }
        else{
          m_Speed = slowSpeed;
        }
    }
  
  
    public void ResetBoost() {
      timesBoosted = 0;
    }
  
    public Command ResetBoostC () {
      return runOnce(this::ResetBoost);
    }
}
