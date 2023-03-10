package com.example.intracer.Fr_groups;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class getcount extends Thread{

    String src;
    String[] check;
    public static ArrayList<UserList> UL;
    ArrayList<String> user;
    public static int userCount=0;

    public void run() {



        FirebaseAuth mb = FirebaseAuth.getInstance();
        FirebaseUser user =  mb.getCurrentUser();
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Groups").child(ActualGroup.Guid).child("Users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("==============================");

                System.out.println((task.getResult().getValue()));
                src = task.getResult().getValue().toString();
                src=src.replace("={",",");
                src=src.replace("{","");
                src=src.replace("}","");
                src=src.trim();

                check = src.split(",");
                userCount = check.length;
                System.out.println(userCount);

            }
        });




    }
}