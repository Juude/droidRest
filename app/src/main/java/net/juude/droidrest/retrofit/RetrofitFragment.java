package net.juude.droidrest.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.juude.droidrest.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by juude on 15-6-18.
 */
public class RetrofitFragment extends Fragment {
    private TextView mCallbackRetro;
    private TextView mObservableRetro;
    private TextView mTextObservable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_retrofit, null);
        mCallbackRetro   = (TextView) v.findViewById(R.id.text_callback);
        mObservableRetro = (TextView) v.findViewById(R.id.text_observable_retro);
        mTextObservable = (TextView) v.findViewById(R.id.text_observable);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://api.github.com")
                .build();
        final NetworkApi api = restAdapter.create(NetworkApi.class);

        Callback<List<Repo>> callBack = new Callback<List<Repo>>() {
            @Override
            public void success(List<Repo> repos, Response response) {
                mCallbackRetro.setText(repos.toString());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        api.listRepos("Juude",  callBack);

        api.listRepos("Juude")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Repo>>() {
                    @Override
                    public void call(List<Repo> repos) {
                        mObservableRetro.setText(repos.toString());
                    }
                });


        Observable.just("fake")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mTextObservable.setText(s);
                    }
                });

        //ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, null);

    }

}
