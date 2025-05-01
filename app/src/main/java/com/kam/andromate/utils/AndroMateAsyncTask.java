package com.kam.andromate.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public abstract class AndroMateAsyncTask<Params, Result> {

    private static final String TAG = "DTVCAsyncTask";
    private final String name;
    private boolean cancelled;
    private Executor threadExecutor = Executors.newSingleThreadExecutor();

    protected AndroMateAsyncTask(String name) {
        this.name = name;
        this.cancelled = false;
    }

    @SafeVarargs
    @WorkerThread
    public final Result doInBackground(Params... p) {
        try {
            return _doInBackground(p);
        } finally {}
    }

    @WorkerThread
    protected abstract Result _doInBackground(Params... p);

    public String getName() {
        return name;
    }

    @MainThread
    protected void onPreExecute() {}

    @SafeVarargs
    public final void executeOnExecutor(Executor executor, Params... params) {
        try {
            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    onPreExecute();
                    if (executor != null)
                        threadExecutor = executor;
                    threadExecutor.execute(() -> {
                        Result result = null;
                        try {
                            result = _doInBackground(params);
                        } catch (Throwable bg_t) {
                            Log.e(TAG, "executeOnExecutor _doInBackground threw: " + bg_t);
                        }
                        Result finalResult = result;
                        new Handler(Looper.getMainLooper()).post(() -> {
                            try {
                                onPostExecute(finalResult);
                            } catch (Throwable t) {
                                Log.e(TAG, "executeOnExecutor onPostExecute threw " + t);
                            }
                        });
                    });
                } catch (Throwable t) {
                    Log.e(TAG, "executeOnExecutor on Handler threw: " + t);
                }
            });
        } catch (Throwable t) {
            Log.e(TAG, "executeOnExecutor threw: " + t);
        }
    }

    @MainThread
    protected void onPostExecute(Result result) {}

    public void cancel(boolean interrupt) {
        cancelled = true;
        if (interrupt) {
            try {
                if (threadExecutor instanceof ThreadPoolExecutor)
                    ((ThreadPoolExecutor) threadExecutor).shutdownNow();
            } catch (Throwable t) {
                Log.e(TAG, "cancel threw " + t);
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    onPostExecute(null);
                } catch (Throwable t) {
                    Log.e(TAG, "cancel onPostExecute threw " + t);
                }
            });
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
