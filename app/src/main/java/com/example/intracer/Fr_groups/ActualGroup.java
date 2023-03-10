package com.example.intracer.Fr_groups;

import com.google.firebase.auth.FirebaseAuth;

public class ActualGroup {
    public static String Gname, Gdesc, Guid, Guser;


    ActualGroup(String uid, String name, String desc, String user){

        this.Guid = uid;
        this.Gname = name;
        this.Gdesc = desc;
        this.Guser = user;

    }
}
