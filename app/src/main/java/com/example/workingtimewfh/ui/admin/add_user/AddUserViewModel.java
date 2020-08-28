package com.example.workingtimewfh.ui.admin.add_user;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class AddUserViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public AddUserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("chukeit");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
