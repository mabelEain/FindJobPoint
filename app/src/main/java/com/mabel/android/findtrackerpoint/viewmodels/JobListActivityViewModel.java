package com.mabel.android.findtrackerpoint.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mabel.android.findtrackerpoint.model.Job;
import com.mabel.android.findtrackerpoint.repositories.JobListRespository;
import com.mabel.android.findtrackerpoint.api.ApiService;

import java.util.List;

public class JobListActivityViewModel extends ViewModel {

    private MutableLiveData<List<Job>> mJobList;
    private JobListRespository mRepo;

    public void init(ApiService apiService){
        if(mJobList != null){
            return;
        }
        mRepo = JobListRespository.getInstance();
        mJobList = mRepo.getAllJob(apiService);
    }

    public MutableLiveData<List<Job>> getmJobList() {
        return mJobList;
    }


}
