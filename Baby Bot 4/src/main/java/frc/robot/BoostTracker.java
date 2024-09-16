public class BoostTracker {
  public class BoostConstants {
    public static final int MAX_BOOSTS = 2;

    public static final int BOOST_TIME = 5;

    private static final double SLOW_SPEED_MPS = 2.8;
    private static final double FAST_SPEED_MPS = 7.1;
  }
    public int timesBoosted = 0;
    public boolean inBoostMode = false;
 public final Trigger trg_canBoost = new Trigger(()->(!inBoostMode && timesBoosted < maxBoosts));
 public Command startBoostC() {
    return Commands.sequence(
      Commands.runOnce(()-> {
        inBoostMode= true;
    }),
    Commands.waitSeconds(BOOST_TIME))
    .finallyDo(()-> {
      ++timesBoosted;
      inBoostMode = false;}
      )
    );
      
  }


  public void resetBoost() {
    timesBoosted = 0;
  }

  public Command resetBoostC () {
    return runOnce(this::resetBoost);
  }
  public double getTopSpeed() {
    return inBoostMode ? FAST_SPEED_MPS : SLOW_SPEED_MPS;
  }
}