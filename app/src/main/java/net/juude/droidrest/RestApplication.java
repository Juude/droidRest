package net.juude.droidrest;

import android.app.Application;

import net.juude.droidrest.volley.ApiManager;

/**
 * Created by juude on 15-5-11.
 */
public class RestApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ApiManager.onApplicationCreate(this);
    }
}
