package com.example.intracer.Fr_chat;

import com.example.intracer.R;
import com.google.firebase.auth.FirebaseAuth;

public class ActualChat {

    public static String Rname, Ruid, Rmail, Suid, Smail;
    private FirebaseAuth mAuth;


    ActualChat(String uid, String mail, String name){
        this.mAuth = FirebaseAuth.getInstance();

        this.Ruid = uid;
        this.Rmail = mail;
        this.Rname = name;

        this.Suid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.Smail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
}
