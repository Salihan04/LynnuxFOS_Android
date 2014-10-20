package com.lynnux.lynnuxfos;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by User on 20-Oct-14.
 */
public class LynnuxFOS_Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "cztzxFVJLJ3PCoSGJeyWU9PX0S8nsNlXtIIwIV98", "VZnqAvCGLZiBaDcrSPFLAY8jgQhP5dwUJdAfRbAx");
    }
}
