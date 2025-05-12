package com.kam.andromate.utils.ThreadUtils;

import android.os.Handler;
import android.os.Looper;

public class AndroMateThread extends Thread{

    private static final Handler UI_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    public AndroMateThread(String name) {
        super(name);
    }

    public AndroMateThread(String name, Runnable r) {
        super(r, name);
    }

    /*
    * launch thread that will execute runnable interface method
     */
    public static AndroMateThread runInBackground(String name, final Runnable runnable) {
        AndroMateThread result =new AndroMateThread(name, runnable);
        result.start();
        return result;
    }

    public static void runOnUiThread(Runnable runnable) {
        UI_THREAD_HANDLER.post(runnable);
    }

}
