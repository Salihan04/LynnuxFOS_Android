package com.lynnux.lynnuxfos;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by User on 20-Oct-14.
 */
public class LynnuxFOS_Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(ParseUser.class);
        Parse.initialize(this, "qjArPWWC0eD8yFmAwRjKkiCQ82Dtgq5ovIbD5ZKW", "Ax0rYmuqAi0NyTNdqeLwJE63IZwvCri69kHtBe2I");
    }
}
