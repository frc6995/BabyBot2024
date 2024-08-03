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

public class PnuematicsS extends SubsystemBase {
  public class Constants{
  }

  private final Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.REVPH, 15);
  private boolean isExtended = false;

  /** Creates a new PnueamticsS. */
  public PnuematicsS() {

  }

  public void extend(){
    isExtended = true;
    m_solenoid.set(true);

  } 

  public void retract(){
    isExtended = false;
    m_solenoid.set(false);
  }

  public Command extendC(){
    return run(this::extend);
  }

  public void toggle(){
    m_solenoid.toggle();
  }

  public Command toggleC() {
    return runOnce(this::toggle);
  }


}