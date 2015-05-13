package net.juude.droidrest.volley;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by juude on 15-5-11.
 */
public class CacheHelper {

    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {

        long now = System.currentTimeMillis();
        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = now + 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired =  now + 60  * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = cacheHitButRefreshed;
        final long ttl = cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;
        return entry;
    }
}
