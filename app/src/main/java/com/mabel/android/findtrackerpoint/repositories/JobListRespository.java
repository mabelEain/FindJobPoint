package com.mabel.android.findtrackerpoint.repositories;

import androidx.lifecycle.MutableLiveData;

import com.mabel.android.findtrackerpoint.model.Job;
import com.mabel.android.findtrackerpoint.api.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JobListRespository {

    private static JobListRespository instance;
    private ArrayList<Job> dataSet  = new ArrayList<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<List<Job>> datas = new MutableLiveData<>();

    public static JobListRespository getInstance(){
        if(instance == null){
            instance = new JobListRespository();
        }
        return instance;
    }



    public MutableLiveData<List<Job>> getAllJob(ApiService apiService) {
        compositeDisposable.add(apiService.getJobs().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Job>>() {
                    @Override
                    public void accept(List<Job> contacts) throws Exception {
                        datas.setValue(contacts);
                    }
                }));
        return datas;
    }
}
