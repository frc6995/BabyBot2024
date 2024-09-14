// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;

import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.pneumatics.PnuematicsS;
import frc.robot.subsystems.pneumatics.Pnuematics2S;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DrivebaseS m_drivebaseS = new DrivebaseS();
  private final PnuematicsS m_pneumatics = new PnuematicsS();
   private final Pnuematics2S m_pneumatics2 = new Pnuematics2S();
  

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    m_drivebaseS.setDefaultCommand(m_drivebaseS.driveC(()->{
      return new ChassisSpeeds(
        MathUtil.applyDeadband (-m_driverController.getLeftY(), 0.2), 
        MathUtil.applyDeadband(m_driverController.getRightX(), 0.2), 
        MathUtil.applyDeadband((m_driverController.getLeftX() * 2), 0.2));
    }));

    //two controllers
    m_operatorController.a().onTrue(m_pneumatics.extendC()).onFalse(m_pneumatics.retractC());
    m_operatorController.x().onTrue(m_pneumatics2.extendC()).onFalse(m_pneumatics2.retractC());

    m_operatorController.b().and(m_drivebaseS.trg_canBoost).onTrue(m_drivebaseS.ToggleFastC());
    m_operatorController.y().onTrue(m_drivebaseS.ResetBoostC());

    /*
    //one controllers
    m_driverController.a().onTrue(m_pneumatics.extendC()).onFalse(m_pneumatics.retractC());
    m_driverController.x().onTrue(m_pneumatics2.extendC()).onFalse(m_pneumatics2.retractC());

    m_driverController.b().and(m_drivebaseS.trg_canBoost).onTrue(m_drivebaseS.ToggleFastC());
    m_driverController.y().onTrue(m_drivebaseS.ResetBoostC()); 
    */

    RobotModeTriggers.disabled().onFalse(m_drivebaseS.ResetBoostC());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Commands.none();
  }

  public void onEnabled() {
    
  }

}
