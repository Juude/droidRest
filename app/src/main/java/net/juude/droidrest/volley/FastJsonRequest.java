package net.juude.droidrest.volley;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by juude on 15-5-11.
 */
public class FastJsonRequest extends Request<JSON>{
    private static final String TAG = "FastJsonRequest";
    private final Response.Listener<JSON> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public FastJsonRequest(int method, String url, Response.Listener<JSON> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public FastJsonRequest(String url, Response.Listener<JSON> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(JSON response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<JSON> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);

        }
        try {
            JSON jsonObject = null;
            if(parsed.startsWith("[")) {
                jsonObject = JSON.parseArray(parsed);
            }else {
                jsonObject = JSON.parseObject(parsed);
            }
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        }catch (JSONException e) {
            Log.e(TAG, "invalid json format", e);
            return null;
        }
    }
}
