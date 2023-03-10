package com.example.intracer.Fr_groups;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Grupos extends Thread{

    public static ArrayList<String> Gid ;
    public static ArrayList<String> Name;
    public static ArrayList<String> Desc;
    public static ArrayList<String> User;
    public static ArrayList<GroupList> OutList;

    ArrayList<String> Gid1 = new ArrayList<>();
    ArrayList<String> Name1 = new ArrayList<>();
    ArrayList<String> Desc1 = new ArrayList<>();
    ArrayList<String> User1 = new ArrayList<>();


    String src;
    String[] check;
    public void run(){
        FirebaseAuth mb = FirebaseAuth.getInstance();
        FirebaseUser user =  mb.getCurrentUser();
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Chats").child(user.getUid()).child("Groups");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){


                    System.out.println("----------------------------");
                    System.out.println((snapshot.getValue()));
                    src = snapshot.getValue().toString();
                    src = src.replace("={", ",");
                    src = src.replace("{", "");
                    src = src.replace("}", "");

                    check = src.split(",");
                    int i = 4;
                    for (String s : check) {
                        s = s.trim();
                        String aux = s.substring(0, 4);

                        if (aux.equals("Desc")) {
                            String[] aus = s.split("=");
                            System.out.println(aus[1] + "\n");
                            Desc1.add(aus[1]);
                        }
                        if (aux.equals("User")) {
                            String[] aus = s.split("=");
                            System.out.println(aus[1] + "\n");
                            User1.add(aus[1]);
                        }
                        if (aux.equals("Name")) {
                            String[] aus = s.split("=");
                            System.out.println(aus[1] + "\n");
                            Name1.add(aus[1]);
                        }
                        if (i == 4) {
                            Gid1.add(s);
                            System.out.println(s + "\n");
                            i = 0;
                        }
                        //System.out.println(s+"\n");
                        i++;
                    }
                    Gid = Gid1;
                    Name = Name1;
                    Desc = Desc1;
                    User = User1;

                    if (Gid.size() == Name.size() && Gid.size() == Desc.size()&& Gid.size() == User.size() ){
                        OutList = new ArrayList<>();
                        for (int e = 0; e < Gid.size(); e++) {
                            OutList.add(new GroupList(Gid.get(e), Name.get(e), Desc.get(e), User.get(e)));
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
