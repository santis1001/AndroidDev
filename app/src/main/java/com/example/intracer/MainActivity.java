package com.example.intracer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intracer.Fr_chat.*;
import com.example.intracer.Fr_groups.*;

import com.example.intracer.UserLogin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Contactos Contactos = new Contactos();
            Contactos.start();
            Grupos grupos = new Grupos();
            grupos.start();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){
                Intent intent = new Intent(MainActivity.this,MenuActivity.class );

                startActivity(intent);
                finish();

                }else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class );
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }



}