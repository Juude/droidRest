package net.juude.droidrest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.okhttp.OkHttpClient;

import net.juude.droidrest.volley.ApiManager;

/**
 * Created by juude on 15-5-11.
 */
public class RestApplication extends Application{

    private static RefWatcher sRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiManager.onApplicationCreate(this);
        Fresco.initialize(getApplicationContext(), new OkHttpImagePipelineConfigFactory().newBuilder(this, new OkHttpClient()).build());
        sRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher() {
        return sRefWatcher;
    }

}
