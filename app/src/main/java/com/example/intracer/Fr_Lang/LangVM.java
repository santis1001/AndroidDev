package com.example.intracer.Fr_Lang;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class LangVM extends ViewModel {

    private MutableLiveData<String> mText;

    public LangVM() {

        mText = new MutableLiveData<>();
        mText.setValue("");


    }


    public LiveData<String> getText() {
        return mText;
    }
}
