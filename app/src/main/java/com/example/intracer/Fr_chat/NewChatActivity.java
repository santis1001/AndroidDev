package com.example.intracer.Fr_chat;

import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewChatActivity extends AppCompatActivity {

    EditText email, message;
    Button Send, Back;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fsdb;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        email =  findViewById(R.id.NC_to);
        Send =  findViewById(R.id.send);
        Back =  findViewById(R.id.back);
        message =  findViewById(R.id.NC_message);

        fsdb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();

        Send.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Validar();
            }
        });
        Back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                atras();
            }
        });
    }
    public void Validar(){
        String mail = email.getText().toString().trim();
        if(mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError(getText(R.string.Toast_Error_correo));
        }else {
            SendMessage(mail, message.getText().toString());
        }

    }

    private void SendMessage(String mail, String message) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> userdb = new HashMap<>();
        Map<String, Object> Currentdb = new HashMap<>();

        String email = user.getEmail();


        DocumentReference docref = fsdb.collection("Users").document(mail);
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String uid = document.getData().get("UID").toString();

                        userdb.put(uid, mail);


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
                        String currenttime = sdf.format(new Date());


                        SimpleDateFormat st = new SimpleDateFormat("HH:mm");
                        String time = st.format(new Date());

                        String auid = mAuth.getUid();

                        myRef.child("Contacts").child(auid).child(uid).setValue(mail);
                        myRef.child("Contacts").child(uid).child(auid).setValue(email);
                        myRef.child("Chats").child(auid).child(uid).child(currenttime).child("Sender").setValue(email);
                        myRef.child("Chats").child(auid).child(uid).child(currenttime).child("Receiver").setValue(mail);
                        myRef.child("Chats").child(auid).child(uid).child(currenttime).child("Message").setValue(message);
                        myRef.child("Chats").child(auid).child(uid).child(currenttime).child("Time").setValue(time);


                        myRef.child("Chats").child(uid).child(auid).child(currenttime).child("Receiver").setValue(mail);
                        myRef.child("Chats").child(uid).child(auid).child(currenttime).child("Sender").setValue(email);
                        myRef.child("Chats").child(uid).child(auid).child(currenttime).child("Message").setValue(message);
                        myRef.child("Chats").child(uid).child(auid).child(currenttime).child("Time").setValue(time);

                        Toast.makeText(NewChatActivity.this,R.string.sent_, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(NewChatActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();

                        Contactos Contactos = new Contactos();
                        Contactos.start();

                    } else {
                        Toast.makeText(NewChatActivity.this,"error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NewChatActivity.this,"error", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onBackPressed(){
        super.onBackPressed();
        atras();
    }

    public void atras(){
        Intent intent = new Intent(NewChatActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}