package com.example.finalp.ui.license;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LicenseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LicenseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("이곳에 오픈소스 라이센스를 입력해주세요.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}