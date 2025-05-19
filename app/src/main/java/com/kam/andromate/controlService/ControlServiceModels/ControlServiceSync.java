package com.kam.andromate.controlService.ControlServiceModels;

import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;
import com.kam.andromate.utils.ThreadUtils.AndroMateSynchronizer;

import java.util.concurrent.TimeoutException;

public class ControlServiceSync {

    private final AndroMateSynchronizer<Boolean, ControlServiceException> sync;

    private static ControlServiceSync instance = null;

    private ControlServiceSync() {
        sync = new AndroMateSynchronizer<>();
    }

    public static ControlServiceSync getInstance() {
        if (instance == null) {
            instance = new ControlServiceSync();
        }
        return instance;
    }

    public AndroMateSynchronizer<Boolean, ControlServiceException> waitNotification(long timeout_ms) throws TimeoutException, ControlServiceException {
        sync.reset();
        sync.wait_notification(timeout_ms);
        if (sync.done()) {
            if (sync.hasError()) {
                throw sync.error;
            }
        } else {
            throw new TimeoutException();
        }
        return sync;
    }

    public void notifyWithError(ControlServiceException e) {
        sync.notifyError(e);
    }

    public void notifyDone() {
        sync.notifySuccess(true);
    }

    public void notifyNotDone() {
        sync.notifySuccess(false);
    }

}
