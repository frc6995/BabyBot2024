package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static edu.wpi.first.wpilibj2.command.Commands.*;
import static frc.robot.BoostTracker.BoostConstants.*;

public class BoostTracker {
  public class BoostConstants {
    public static final int MAX_BOOSTS = 2;

    public static final int BOOST_TIME = 5;

    private static final double SLOW_SPEED_MPS = 2.8;
    private static final double FAST_SPEED_MPS = 7.1;
  }

  public int timesBoosted = 0;
  public boolean inBoostMode = false;
  public final Trigger trg_canBoost = new Trigger(() -> (!inBoostMode && (timesBoosted < MAX_BOOSTS)));

  public Command startBoostC() {
    return sequence(
        runOnce(() -> {
          inBoostMode = true;
        }),
        waitSeconds(BOOST_TIME))
        .finallyDo((interrupt) -> {
          if (!interrupt) {
            ++timesBoosted;
          }
          inBoostMode = false;
        });

  }

  public void resetBoost() {
    timesBoosted = 0;
  }

  public Command resetBoostC() {
    return runOnce(this::resetBoost);
  }

  public double getTopSpeed() {
    return inBoostMode ? FAST_SPEED_MPS : SLOW_SPEED_MPS;
  }
}