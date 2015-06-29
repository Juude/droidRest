package net.juude.droidrest.retrofit;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by juude on 15-6-18.
 */
public interface NetworkApi {
    @GET("/users/{user}/repos")
    void listRepos(@Path("user") String user, Callback<List<Repo>> callback);

    //@GET("/users/{user}/repos")
    //List<Repo> listRepos(@Path("user") String user);

    @GET("/users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
}
