package net.juude.droidrest.multithread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.droidrest.R;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Created by juude on 15-7-1.
 */
public class ConcurrentFragment extends Fragment{

    private static final String TAG = "ConcurrentFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_concurrent, null);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ArrayList<Future> futures = new ArrayList();
        for(int i = 0; i < 3; i++) {
            Callable callable = new SubCallable("name" + i);
            Future task = executorService.submit(callable);
            futures.add(task);
        }
        try {
            Log.d(TAG, " first result : " + futures.get(0).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class SubCallable implements Callable<String> {
        private final String mName;
        private int mCount = 1;
        public SubCallable(String name) {
            mName = name;
        }

        @Override
        public String call() throws Exception {
            for(int i = 0; i < 10000; i++) {
                mCount += 1;
                Thread.sleep(1);
            }
            Log.d(TAG, " result : " + mName + mCount);

            return mName + mCount;
        }
    }

}
