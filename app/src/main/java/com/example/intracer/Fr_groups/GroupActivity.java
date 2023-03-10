package com.example.intracer.Fr_groups;

import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupActivity extends AppCompatActivity {

    private static boolean exist = false;

    private Button Send, Back;
    FloatingActionButton fabadd, fabqr, fabscan, fablist;
    private TextView nm;
    private EditText Msj;
    private static RecyclerView recyclerView;
    int counter;

    private static groupmsgAdapter listadapter;

    private static ArrayList<groupmsg> MsgList = Getmsg.MsgList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        exist = true;

        nm = findViewById(R.id.groupname);
        fabadd  = findViewById(R.id.fabaddP);
        fabqr  = findViewById(R.id.fabQR);
        fabscan  = findViewById(R.id.fabscanQR);
        fablist  = findViewById(R.id.fablist);

        Msj =  findViewById(R.id.txtMensaje);
        recyclerView = findViewById(R.id.chatview);

        listadapter = new groupmsgAdapter(Getmsg.MsgList, this);


        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(listadapter);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();

        counter=0;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Groups").child(ActualGroup.Guid).child("Msg");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(counter==4){

                    recyclerView.getAdapter().notifyDataSetChanged();

                    System.out.println(recyclerView.getAdapter().getItemCount()+"por dentro");
                    listadapter = new groupmsgAdapter(Getmsg.MsgList, GroupActivity.this);
                    LinearLayoutManager lm = new LinearLayoutManager(GroupActivity.this);
                    lm.setStackFromEnd(true);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(listadapter);

                    counter=0;
                }
                counter++;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nm.setText(ActualGroup.Gname);

        if(ActualGroup.Guser.equals("admin")){
            FabMethod();
        }else{
            UserFab();
        }
        Send = findViewById(R.id.send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message = Msj.getText().toString().trim();
               if(message.length()>0){
                   enviar();
               }
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
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private void enviar() {

        String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String msgj = Msj.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
        SimpleDateFormat Sdd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currenttime = sdf.format(new Date());
        String hex = Long.toHexString(Long.parseLong(currenttime))+"";
        long ct =Long.parseLong(sdf.format(new Date()));
        String date = Sdd.format(new Date());

        myRef.child("Groups").child(ActualGroup.Guid).child("Msg").child(hex).child("Name").setValue(mail);
        myRef.child("Groups").child(ActualGroup.Guid).child("Msg").child(hex).child("Msg").setValue(msgj);
        myRef.child("Groups").child(ActualGroup.Guid).child("Msg").child(hex).child("Time").setValue(date);
        myRef.child("Groups").child(ActualGroup.Guid).child("Msg").child(hex).child("Key").setValue(hex);



        Msj.setText("");
    }

    private void UserFab() {

        fabadd.setVisibility(View.GONE);
        fabqr.setVisibility(View.GONE);
        fablist.setVisibility(View.GONE);

        fabscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GroupActivity.this, ScanQRActivity.class );
                startActivity(intent);
                finish();            }
        });

    }

    private void FabMethod() {

        fabscan.setVisibility(View.GONE);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupActivity.this, MembersActivity.class );
                startActivity(intent);
                finish();
            }
        });
        fabqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GroupActivity.this, QrActivity.class );
                startActivity(intent);
                finish();            }
        });

        fablist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateList dl = new DateList();
                dl.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(GroupActivity.this, AttendanceList.class );
                        startActivity(intent);
                        finish();
                    }
                }, 1000);

            }
        });

    }





    public void onBackPressed(){
        super.onBackPressed();
        atras();

    }

    public void atras(){
        Intent intent = new Intent(GroupActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}