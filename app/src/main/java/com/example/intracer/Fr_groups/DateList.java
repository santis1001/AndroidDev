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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class DateList extends Thread{

    String src;
    String[] check;
    public static ArrayList<QRlist> DL;
    ArrayList<QRlist> aux = new ArrayList<>();
    public void run() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Groups").child(ActualGroup.Guid).child("QR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //System.out.println("==============================");

                    //System.out.println((task.getResult().getValue()));
                    src = snapshot.getValue().toString();
                    System.out.println(src);

                    src = src.replace("={", ",");
                    src = src.replace("{", "");
                    src = src.replace("}", "");
                    src = src.trim();
                    check = src.split(",");
                    int i = 5;
                    for (String s : check) {
                        //System.out.println(s.trim());
                        if (i == 5) {
                            i = 0;
                            s = s.trim();
                            String adt = s.substring(0, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 8) + " " + s.substring(8, 10) + ":" + s.substring(10, 12);

//                            String num = "123456789012";
//                            String tr = num.substring(0, 4) + "-" + num.substring(4, 6) + "-" + num.substring(6, 8) + " " + num.substring(8, 10) + ":" + num.substring(10, 12);
//                            System.out.println("=>"+tr);
                            String hex = s.trim();
                            try {
                                hex = Long.toHexString(Long.parseLong(s.trim())) + "";
                            }catch (NumberFormatException e){

                            }

                            System.out.println(adt + "----" + hex);
                                aux.add(new QRlist(adt, s.trim()));

                        }
                        i++;
                    }

                }


                Collections.sort(aux);

                for(QRlist q:aux){
                    System.out.println("------------------------------");

                    System.out.println(q.getDateSN());
                }
                DL=new ArrayList<>();

                DL=aux;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
