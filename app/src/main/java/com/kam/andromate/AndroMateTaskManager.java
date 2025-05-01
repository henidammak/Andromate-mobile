package com.kam.andromate;

import com.kam.andromate.utils.AndroMateAsyncTask;
import com.kam.andromate.view.MainReportSection;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AndroMateTaskManager {

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
			1, 20, 3,TimeUnit.SECONDS, new SynchronousQueue<>(),
			new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    });

    private final MainReportSection rs;

    public AndroMateTaskManager(MainReportSection reportSection) {
        this.rs = reportSection;
    }

    public void startTask(String name) {
        AndroMataTaskExecutor androMateTaskExecutor = new AndroMataTaskExecutor(name);
        androMateTaskExecutor.executeOnExecutor(THREAD_POOL_EXECUTOR, "");
    }

    private class AndroMataTaskExecutor extends AndroMateAsyncTask<String, String> {


        protected AndroMataTaskExecutor(String name) {
            super(name);
        }

        @Override
        protected String _doInBackground(String... p) {
            if (IConstants.CHECK_DEVICE_AUTHORIZATION) {
                rs.appendTitle("check authorization ....");
                try {
                    AndroMateManager.checkAuthorization(IConstants.DEFAULT_AUTHORIZATION_URL);
                } catch (Exception e) {
                    rs.errorMsg("error on get autorization "+e);
                }
            } else {
                rs.appendTitle("wait for incoming msg");
            }
            return "";
        }

    }

}
