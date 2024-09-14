
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.pneumatics;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pnuematics3S extends SubsystemBase {
  public class Constants{
  }

  private final Solenoid m_solenoid3Solenoid = new Solenoid(PneumaticsModuleType.REVPH,4);
  private boolean isExtended = false;

  /** Creates a new PnueamticsS. */
  public Pnuematics3S() {

  }

  public void extend(){
    isExtended = true;
    m_solenoid3Solenoid.set(true);

  } 

  public void retract(){
    isExtended = false;
    m_solenoid3Solenoid.set(false);
  }

  public Command extendC(){
    return run(this::extend);
  }

  public Command retractC(){
    return run(this::retract);
  }
  public void toggle(){
    m_solenoid3Solenoid.toggle();
  }

  public Command toggleC() {
    return runOnce(this::toggle);
  }


}
