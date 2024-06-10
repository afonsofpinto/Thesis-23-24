package pt.tecnico.blockchain;

import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;

public class IbftTimer {

    private static Timer _timer;

    public static void start(int round) {
        _timer = new Timer();
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Logger.logDebug("Round Timer Expired: Triggering <ROUND-CHANGE>");
            }
        }, (long)Math.exp(round) * 1000);
    }

    public static void stop() {
        Logger.logDebug("Stopping Round Timer before <ROUND-CHANGE>");
        _timer.cancel();
    }
}
