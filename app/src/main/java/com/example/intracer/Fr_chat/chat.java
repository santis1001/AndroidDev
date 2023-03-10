package com.example.intracer.Fr_chat;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class chat extends Thread {

    ArrayList<msj> Mlist;
    String check[];
    public static ArrayList<msj> finallist;
    ArrayList<String> sender ;
    ArrayList<String> message;
    ArrayList<String> time  ;
    ArrayList<String> key  ;

    public void run(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Chats/"+ActualChat.Suid+"/"+ActualChat.Ruid);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot SS) {
                if (SS.exists()) {
                    sender = new ArrayList<>();
                    message = new ArrayList<>();
                    time = new ArrayList<>();
                    key = new ArrayList<>();


                    //System.out.println("--------------------\n" + SS.getValue());
                    String src = SS.getValue().toString();

                    src = src.replace("}", "\n");
                    src = src.replace("={", ",");
                    src = src.replace("{", "");
                    src = src.trim();
                    //System.out.println("--------------------\n" + src);
                    check = src.split("\n");

                    for (String s : check) {
                        s = s.trim();
                        System.out.println("=>" + s);
                        String [] auxs = s.split(",");
                        for(String a: auxs) {
                            if (a.contains("=")){
                                String aux[] = a.split("=");
                                //System.out.println("a => "+a);

                                //System.out.println("=>"+ aux[0]+"//"+aux[1]);

                                if (aux[0].contains("Sender")) {
                                    sender.add(aux[1]);
                                    //System.out.println("Sender:"+sender.get(sender.size()-1));
                                }else if (aux[0].contains("Message")) {
                                    message.add(aux[1]);
                                   // System.out.println("Message:"+message.get(message.size()-1));
                                }else if (aux[0].contains("Time")) {
                                    time.add(aux[1]);
                                   // System.out.println("Time:"+time.get(time.size()-1));
                                }
                            }else {
                                if(a.length()>8){
                                key.add("A"+a.trim());
                                System.out.println(""+a.trim());
                                }

                            }
                            System.out.println("==>"+sender.size()+ " " + message.size() + " " + time.size()+ " " + key.size());
                        }
                    }
                    Mlist = new ArrayList<>();

                    if (message.size() == time.size() && message.size() == sender.size() && message.size() == key.size()) {
                        System.out.println("Entro");

                        int e = 0;
                        for (String s : sender) {
                            //System.out.println("---------------------------" + sender.get(e) + "//" + message.get(e) + "//" + time.get(e));
                            msj ms = new msj(message.get(e), time.get(e), s, key.get(e));
                            Mlist.add(ms);
                            e++;
                        }
                        //System.out.println("" + Mlist.size() + "  " + time.size());

                        Collections.sort(Mlist);
                    }

                    finallist = new ArrayList<>();
                    finallist = Mlist;
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(""+error);

            }
        });

    }


}
