package net.juude.droidrest.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by juude on 15-5-11.
 */
public class Toaster {
    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
