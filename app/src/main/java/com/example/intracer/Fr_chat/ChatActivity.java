package com.example.intracer.Fr_chat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intracer.Fr_groups.ActualGroup;
import com.example.intracer.Fr_groups.Getmsg;
import com.example.intracer.Fr_groups.GroupActivity;
import com.example.intracer.Fr_groups.groupmsgAdapter;
import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.core.Query;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private Button Send, Back, Cam;
    private TextView nm;
    private String Rname, Ruid, Rmail, Suid, Smail, message;
    private EditText Msj;

    private RecyclerView recyclerView;
    public static int classeActiva = 0;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    int counter;
    msjAdapter listadapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Contactos Contactos = new Contactos();
        Contactos.start();

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();



        nm = findViewById(R.id.chatname);
        Send =  findViewById(R.id.send);
        Back =  findViewById(R.id.back);
        Cam =  findViewById(R.id.Vcam);

        Msj =  findViewById(R.id.txtMensaje);
        recyclerView = findViewById(R.id.chatview);

        listadapter = new msjAdapter(chat.finallist, this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(listadapter);


        nm.setText(ActualChat.Rname);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();

        counter=0;
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference("Chats/"+ActualChat.Suid+"/"+ActualChat.Ruid);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(counter==4){

                    recyclerView.getAdapter().notifyDataSetChanged();

                    System.out.println(recyclerView.getAdapter().getItemCount()+"por dentro");
                    listadapter = new msjAdapter(chat.finallist, ChatActivity.this);
                    LinearLayoutManager lm = new LinearLayoutManager(ChatActivity.this);
                    lm.setStackFromEnd(true);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(listadapter);

                    counter=0;
                }
                counter++;
                System.out.println("count: "+counter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Send.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Validar();
            }
        });

        Cam.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Vllamada();
            }


        });

        Back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                atras();
            }
        });
    }



    private void Vllamada() {
        Intent intent = new Intent(ChatActivity.this, VideoChatActivity.class);
        startActivity(intent);
        finish();
    }


    private void Validar(){
        message = Msj.getText().toString().trim();
        if(message.length()>0){
            SendMessage(ActualChat.Rmail, message);
        }
    }
    private void SendMessage(String mail, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
        String currenttime = sdf.format(new Date());


        SimpleDateFormat st = new SimpleDateFormat("HH:mm");
        String time = st.format(new Date());

//        Message Smsj = new Message(time, message, ActualChat.Smail, ActualChat.Rmail);
//        Message Rmsj = new Message(time, message, ActualChat.Smail, ActualChat.Rmail);

        //list.add(C1.addMsj(message, time, ActualChat.Smail));

//        myRef.child("Chats").child(ActualChat.Suid).child(ActualChat.Ruid).child(currenttime).setValue(Smsj);
//        myRef.child("Chats").child(ActualChat.Ruid).child(ActualChat.Suid).child(currenttime).setValue(Rmsj);

        myRef.child("Chats").child(ActualChat.Suid).child(ActualChat.Ruid).child(currenttime).child("Sender").setValue(ActualChat.Smail);
        myRef.child("Chats").child(ActualChat.Suid).child(ActualChat.Ruid).child(currenttime).child("Receiver").setValue(ActualChat.Rmail);
        myRef.child("Chats").child(ActualChat.Suid).child(ActualChat.Ruid).child(currenttime).child("Message").setValue(message);
        myRef.child("Chats").child(ActualChat.Suid).child(ActualChat.Ruid).child(currenttime).child("Time").setValue(time);


        myRef.child("Chats").child(ActualChat.Ruid).child(ActualChat.Suid).child(currenttime).child("Receiver").setValue(ActualChat.Rmail);
        myRef.child("Chats").child(ActualChat.Ruid).child(ActualChat.Suid).child(currenttime).child("Sender").setValue(ActualChat.Smail);
        myRef.child("Chats").child(ActualChat.Ruid).child(ActualChat.Suid).child(currenttime).child("Message").setValue(message);
        myRef.child("Chats").child(ActualChat.Ruid).child(ActualChat.Suid).child(currenttime).child("Time").setValue(time);
        Msj.setText("");


    }




    public void onBackPressed(){
        super.onBackPressed();
        classeActiva=0;
        atras();

    }

    public void atras(){
        classeActiva =0;
        Intent intent = new Intent(ChatActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}