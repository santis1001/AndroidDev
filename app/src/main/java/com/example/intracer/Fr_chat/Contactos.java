package com.example.intracer.Fr_chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.intracer.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractCollection;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Contactos extends Thread{

    public static int i, contador, cant, lenght;
    public static String doc, src;
    public static String check[];

    public static ArrayList<String> uid = new ArrayList<>();
    public static ArrayList<String> mail = new ArrayList<>();
    public static ArrayList<String> name = new ArrayList<>();
    public ArrayList<String> Cont = new ArrayList<>();

    public ArrayList<String> uid1 = new ArrayList<>();
    public ArrayList<String> mail1 = new ArrayList<>();
    public ArrayList<String> name1 = new ArrayList<>();

    public void run(){

        FirebaseAuth mb = FirebaseAuth.getInstance();
        FirebaseUser user =  mb.getCurrentUser();
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        contador=0;
        lenght=0;

        db.child("Contacts").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            public void onComplete(Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    //Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if (task.getResult().exists()){
                            System.out.println((task.getResult().getValue()));
                        src = task.getResult().getValue().toString();

                        check = src.split(" ");
                        cant = check.length;
                        lenght = check.length;
                        for (int i = 0; i < check.length; i++) {
                            if (i == 0) {
                                check[i] = check[i].substring(1, check[i].length() - 1);
                            } else {
                                check[i] = check[i].substring(0, check[i].length() - 1);

                                for (i = 0; i < lenght; i++) {

                                    String aux[] = check[i].split("=");

                                    String auxmail = aux[1];

                                    if (check_mail(auxmail)) {

                                        DocumentReference docref = fs.collection("Users").document(auxmail);
                                        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        if (contador <= cant) {
                                                            doc = document.getData().get("Name").toString();
                                                            uid1.add(aux[0]);
                                                            mail1.add(aux[1]);
                                                            name1.add(doc);

                                                            uid = uid1;
                                                            mail = mail1;
                                                            name = name1;
                                                            Create_contact(aux[1], aux[0], doc);
                                                            System.out.println("----------");
                                                        } else {

                                                        }
                                                    } else {
                                                        System.out.println("error Contactos");
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                        }
                    }
                }
            }
        });
    }

    public boolean check_mail(String Cmail){


        String p = "src/main/res/"+Cmail+".txt";
        Path ruta = Paths.get(p);
        if(Files.exists(ruta)){
            return false;
        }else{
            return true;
        }
    }
    public void Create_contact(String Cmail, String Cuid, String CName){


        String p = Cmail;
        try {
            Writer wt = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(p),"utf-8"));
            String Linea = Cuid+"\n"+Cmail+"\n"+CName;
            wt.write(Linea);
            wt.close();
        } catch (IOException e) {
            System.out.println("no jala");
        }
    }

}
