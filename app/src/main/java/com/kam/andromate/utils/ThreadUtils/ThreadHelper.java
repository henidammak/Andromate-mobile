package com.kam.andromate.utils.ThreadUtils;

public class ThreadHelper {

    /*
    * sleep timeToSleep (ms)
    * */
    public static void deepSleep(long timeToSleep_ms) {
        long wakeTime = System.currentTimeMillis()+timeToSleep_ms;
        while (System.currentTimeMillis()<wakeTime) {
            try {
                final long realTimeToSleep_ms = wakeTime - System.currentTimeMillis();
                if (realTimeToSleep_ms>0) Thread.sleep(realTimeToSleep_ms);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
