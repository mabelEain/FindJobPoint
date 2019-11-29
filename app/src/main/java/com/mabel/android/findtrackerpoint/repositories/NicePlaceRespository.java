package com.mabel.android.findtrackerpoint.repositories;

import androidx.lifecycle.MutableLiveData;

import com.mabel.android.findtrackerpoint.model.NicePlace;

import java.util.ArrayList;
import java.util.List;

public class NicePlaceRespository {

    private static NicePlaceRespository instance;
    private ArrayList<NicePlace> dataSet  = new ArrayList<>();

    public static NicePlaceRespository getInstance(){
        if(instance == null){
            instance = new NicePlaceRespository();
        }
        return instance;
    }

    //get data from online source
    public MutableLiveData<List<NicePlace>> getNicePlaces(){
        setNicePlaces();

        MutableLiveData<List<NicePlace>> datas = new MutableLiveData<>();
        datas.setValue(dataSet);
        return datas;
    }

    private void setNicePlaces(){
        dataSet.add(
                new NicePlace("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
                        "Havasu Falls")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/tpsnoz5bzo501.jpg",
                        "Trondheim")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/qn7f9oqu7o501.jpg",
                        "Portugal")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/j6myfqglup501.jpg",
                        "Rocky Mountain National Park")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/0h2gm1ix6p501.jpg",
                        "Havasu Falls")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/k98uzl68eh501.jpg",
                        "Mahahual")
        );
        dataSet.add(
                new NicePlace("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
                        "Frozen Lake")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/obx4zydshg601.jpg",
                        "Austrailia")
        );
    }
}
