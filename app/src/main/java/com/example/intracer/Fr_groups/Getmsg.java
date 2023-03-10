package com.example.intracer.Fr_groups;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Key;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.SplittableRandom;

public class Getmsg extends Thread{

    String src;
    String[] check;
    public static ArrayList<groupmsg> MsgList = new ArrayList<>();
    ArrayList<String> email;
    ArrayList<String> date;
    ArrayList<String> msg;
    ArrayList<String> key;

    ArrayList<groupmsg> aux;

    public void run() {
//        FirebaseAuth mb = FirebaseAuth.getInstance();
//        FirebaseUser user =  mb.getCurrentUser();
//        FirebaseFirestore fs = FirebaseFirestore.getInstance();
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//        db.child("Groups").child(ActualGroup.Guid).child("Msg").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                dosimon(task.getResult().getValue().toString());
//            }
//        });

        GetMSj();


    }

    private void GetMSj(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Groups").child(ActualGroup.Guid).child("Msg");
        db.orderByChild("/Key").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot SS) {
                if (SS.exists()){
                    System.out.println("================\n"+SS.getValue().toString());
                    email = new ArrayList<>();
                    date = new ArrayList<>();
                    msg = new ArrayList<>();
                    key = new ArrayList<>();


                    src = SS.getValue().toString();

                    src=src.replace("={",",");
                    src=src.replace("{","");
                    src=src.replace("}","");
                    src=src.trim();
                    check = src.split(",");
                    int i=5;
                    for(String s: check){

                        s=s.trim();
                        String aux[] = s.split("=");

                        if(aux[0].equals("Name")){
                            //System.out.println(s.trim());
                            //System.out.println(aux[1]);
                            email.add(aux[1]);

                        }
                        if(aux[0].equals("Time")){
                            //System.out.println(s.trim());
                            // System.out.println(aux[1]);
                            date.add(aux[1]);
                        }
                        if(aux[0].equals("Msg")){
                            //System.out.println(s.trim());
                            //System.out.println(aux[1]);
                            msg.add(aux[1]);
                        }
                        if(aux[0].equals("Key")){
                            //System.out.println(s.trim());
                            System.out.println(aux[1]);
                           // Long num = Long.parseLong(aux[1],10);
                            key.add(aux[1]);
                        }


                    }
                    aux = new ArrayList<>();
                    System.out.println("==========Lenghts========\n" +
                            "Key: "+key.size()+"\n"+
                            "email: "+email.size()+"\n"+
                            "mgs: "+msg.size()+"\n" +
                            "date: "+date.size());


                    if (email.size()==msg.size() && email.size()==date.size() && email.size() == key.size()){

                        int e=0;

                        for(String s:email){


                            String as =s;
                            System.out.print(as+"===");

                            String am =msg.get(e);
                            System.out.print(am+"===");

                            String ad = date.get(e);
                            System.out.print(ad+"===");

                            String ky = key.get(e)+"";
                            System.out.print(ky+"===\n");
                            //groupmsg gp=new groupmsg(s,msg.get(e),date.get(e));
                            groupmsg gp=new groupmsg(ky,as,am,ad);
                            System.out.println(e+"==="+gp.getEmail()+"==="+gp.getMsg()+"==="+gp.getDate()+"===");


                            aux.add(gp);
                            e++;
                        }

                        Collections.sort(aux);

                    }

                        MsgList = new ArrayList<>();
                        MsgList = aux;
                        //GroupActivity.dosimon();


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void dosimon(String SS){
        //System.out.println("==============================");

        //System.out.println((task.getResult().getValue()));

    }
}
