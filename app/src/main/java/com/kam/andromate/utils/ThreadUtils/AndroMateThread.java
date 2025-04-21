package com.kam.andromate.utils.ThreadUtils;

public class AndroMateThread extends Thread{

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
        AndroMateThread result =new AndroMateThread(name) {
            public void dorun() {
                runnable.run();
            }
        };
        result.start();
        return result;
    }

}
