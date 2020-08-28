package com.example.workingtimewfh.ui.user.leave;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeaveViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LeaveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("chukeit");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
