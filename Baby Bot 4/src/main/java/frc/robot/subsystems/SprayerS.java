// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SprayerS extends SubsystemBase {

  // don't construct this before the SprayerS constructor. 
  private final Solenoid m_solenoid;

  /** Creates a new PnueamticsS. */
  public SprayerS(int channel) {
    m_solenoid = new Solenoid(PneumaticsModuleType.REVPH, channel);
  }

  public void spray(){
    m_solenoid.set(true);
  } 

  public void stop(){
    m_solenoid.set(false);
  }

  public Command sprayC(){
    return runOnce(this::spray);
  }

  public Command stopC(){
    return runOnce(this::stop);
  }

  public Command sprayStopC() {
    return startEnd(this::spray, this::stop);
  }

// toggles are unnecessary for our usage


}
