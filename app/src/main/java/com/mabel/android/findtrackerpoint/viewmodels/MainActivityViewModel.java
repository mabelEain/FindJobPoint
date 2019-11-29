package com.mabel.android.findtrackerpoint.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mabel.android.findtrackerpoint.model.NicePlace;
import com.mabel.android.findtrackerpoint.repositories.NicePlaceRespository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<NicePlace>> mNicePlaces;
    private NicePlaceRespository mRepo;

    public void init(){
        if(mNicePlaces != null){
            return;
        }
        mRepo = NicePlaceRespository.getInstance();
        mNicePlaces = mRepo.getNicePlaces();
    }

    public MutableLiveData<List<NicePlace>> getmNicePlaces() {
        return mNicePlaces;
    }


}
