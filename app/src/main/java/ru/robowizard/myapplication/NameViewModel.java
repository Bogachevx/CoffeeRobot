package ru.robowizard.myapplication;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class NameViewModel extends ViewModel {
    private MutableLiveData<String> currentName;

    public MutableLiveData<String> getCurrentName(){
        if(currentName == null){
            currentName = new MutableLiveData<>();
        }
        return currentName;
    }
}
