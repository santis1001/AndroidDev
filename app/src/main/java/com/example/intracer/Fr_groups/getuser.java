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
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class getuser extends Thread{

    String src;
    String[] check;
    public static  ArrayList<UserList> UL;
    ArrayList<String> email;
    ArrayList<String> date;
    ArrayList<UserList> aux;

    public void run() {

        email = new ArrayList<>();
        date = new ArrayList<>();

        FirebaseAuth mb = FirebaseAuth.getInstance();
        FirebaseUser user =  mb.getCurrentUser();
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Groups").child(ActualGroup.Guid).child("QR").child(AttendanceList.SNDate).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("==============================");

                //System.out.println((task.getResult().getValue()));
                src = task.getResult().getValue().toString();
                src=src.replace("={",",");
                src=src.replace("{","");
                src=src.replace("}","");
                src=src.trim();
                check = src.split(",");
                int i=4;


                for(String s: check){

                    s=s.trim();
                    String aux[] = s.split("=");
                    if(aux[0].equals("Email")){
                        //System.out.println(s.trim());
                        System.out.println(aux[1]);
                        email.add(aux[1]);

                    }
                    if(aux[0].equals("DateSc")){
                        //System.out.println(s.trim());
                        System.out.println(aux[1]);
                        date.add(aux[1]);
                    }

                }
                aux = new ArrayList<>();
                int e=0;
                for(String s:email){
                    aux.add(new UserList(s,date.get(e)));
                    e++;
                }
                UL = aux;

            }
        });




    }
}
