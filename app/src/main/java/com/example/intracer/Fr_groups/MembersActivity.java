package com.example.intracer.Fr_groups;
import com.example.intracer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MembersActivity extends AppCompatActivity {

    EditText email;
    private Button Back;
    FloatingActionButton Send;

    private FirebaseDatabase db;
    private DatabaseReference myRef;
    FirebaseUser user;
    FirebaseAuth mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);


        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();

        mb = FirebaseAuth.getInstance();
        user =  mb.getCurrentUser();

        email =  findViewById(R.id.email_member);
        Send =  findViewById(R.id.send);
        Send.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Validar();
            }
        });
        Back =  findViewById(R.id.back);
        Back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                atras();
            }
        });
    }
    String check[];
    ArrayList<String> mails;

    public void Validar(){
        String src = email.getText().toString().trim();

        if (!src.isEmpty()) {
            check = src.split(" ");
            mails = new ArrayList<>();

            for (int i = 0; i < check.length; i++) {
                mails.add(check[i]);
                if (!Patterns.EMAIL_ADDRESS.matcher(check[i]).matches()) {
                    email.setError(getText(R.string.Toast_Error_correo));
                } else {
                    addtoGroup(ActualGroup.Gname.trim(), ActualGroup.Gdesc.trim(), mails);
                }
            }
        }

    }

    private void addtoGroup(String Name, String desc, ArrayList<String> mails) {

        ArrayList<String> Ruid= new ArrayList<>();

        System.out.println("------------------");
        int i;


        for (i=0;i<mails.size();i++) {

            FirebaseFirestore fsdb = FirebaseFirestore.getInstance();
            DocumentReference docref = fsdb.collection("Users").document(mails.get(i));

            int finalI = i;
            docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        String SRCuid = document.getData().get("UID").toString();
                        Ruid.add(SRCuid);
                        System.out.println(mails.get(finalI)+"  "+SRCuid);

                        myRef.child("Groups").child(ActualGroup.Guid).child("Users").child(SRCuid).setValue("null");
                        myRef.child("Chats").child(SRCuid).child("Groups").child(ActualGroup.Guid).child("Name").setValue(Name);
                        myRef.child("Chats").child(SRCuid).child("Groups").child(ActualGroup.Guid).child("Desc").setValue(desc);
                        myRef.child("Chats").child(SRCuid).child("Groups").child(ActualGroup.Guid).child("User").setValue("null");

                    }
                }
            });
        }
        atras();
    }

    public void onBackPressed(){
        super.onBackPressed();
        atras();
    }

    public void atras(){
        Intent intent = new Intent(MembersActivity.this, GroupActivity.class);
        startActivity(intent);
        finish();
    }
}