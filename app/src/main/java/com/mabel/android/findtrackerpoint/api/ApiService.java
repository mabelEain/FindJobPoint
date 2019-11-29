package com.mabel.android.findtrackerpoint.api;

import com.mabel.android.findtrackerpoint.model.Job;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("8d195.json")
    Observable<List<Job>> getJobs();
}
