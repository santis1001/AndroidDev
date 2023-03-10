package com.example.intracer.Fr_Logout;

import android.content.DialogInterface;
import android.content.Intent;
import com.example.intracer.R;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutVM extends ViewModel {

  private MutableLiveData<String> mText;

    public LogoutVM() {

        FirebaseAuth user = FirebaseAuth.getInstance();
        String getmail = user.getCurrentUser().getEmail();

        mText = new MutableLiveData<>();
        mText.setValue(getmail);


    }


    public LiveData<String> getText() {
        return mText;
    }
}
