package com.kam.andromate.utils.ThreadUtils;

import android.util.Log;
import com.kam.andromate.IConstants;

public class AndroMateSynchronizer<RESULT,ERROR> {

    private static final String TAG = "Synchronizer";
    public boolean success;
    public RESULT result;
    public ERROR error;
    private boolean done=false;

    public void reset() {
        success=false;
        result=null;
        error=null;
        done=false;
    }

    public synchronized void notifyStatus(boolean status) {
        if (done) Log.e(TAG, "already notified",new Throwable());
        this.done=true;
        this.result=null;
        this.error=null;
        this.success = status;
        notify();
    }

    public synchronized void notifySuccess(RESULT result) {
        if (done) {
            return;
        }
        this.done=true;
        this.result = result;
        this.success = true;
        this.error=null;
        notify();
    }

    public synchronized void notifyError(ERROR error) {
        if (done) {
            if (IConstants.DEBUG) Log.e(TAG, "already notified",new Throwable());
            return;
        }
        this.done=true;
        this.result=null;
        this.error=error;
        this.success = false;
        notify();
    }

    public synchronized void wait_notification() {
        while (!done) { try {wait();} catch (Throwable ignored) {} }
    }

    public synchronized void wait_notification(long timeout_ms) {
        long endTime=timeout_ms+System.currentTimeMillis();
        while (!done && endTime>System.currentTimeMillis()) {
            long timeToWait_ms = endTime-System.currentTimeMillis();
            if (timeToWait_ms<=0) break;
            try {wait(timeToWait_ms);} catch (Throwable ignored) {}
        }
    }

    public boolean done() {
        return done;
    }

    public boolean hasError() { return !success;}

}
