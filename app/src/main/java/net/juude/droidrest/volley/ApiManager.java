package net.juude.droidrest.volley;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by juude on 15-5-11.
 */
public class ApiManager {



    private static RequestQueue sRequestQueue;

    public static void onApplicationCreate(Application application) {
        sRequestQueue = Volley.newRequestQueue(application);
    }

    public static void addRequest(Request request) {
        sRequestQueue.add(request);
    }
}
