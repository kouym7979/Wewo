package com.example.finalp.ui.myinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyinfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyinfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("내정보 페이지 입니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}