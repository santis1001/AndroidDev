package com.example.intracer.Fr_groups;
import com.example.intracer.MainActivity;
import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.example.intracer.UserLogin.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceList extends AppCompatActivity {



    private RecyclerView recyclerView;
    private ListAdapter listadapter;
    private Button Back, Search, Cancel, Ascending, Descending;
    private TextView txtSearch;
    public static String Date, SNDate;
    public static  getuser gu;
    public static  getcount gc;
    ArrayList<QRlist> date = DateList.DL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);


        listadapter = new ListAdapter(DateList.DL, this);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        listadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date = date.get(recyclerView.getChildAdapterPosition(v)).getDate();
                SNDate = date.get(recyclerView.getChildAdapterPosition(v)).getDateSN();

                gu = new getuser();
                gu.start();
                gc = new getcount();
                gc.start();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AttendanceList.this, ListActivity.class );
                        startActivity(intent);
                        finish();

                    }
                }, 1000);

            }
        });
        recyclerView.setAdapter(listadapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener( ));

        txtSearch = findViewById(R.id.txtSearch);

        Cancel = findViewById(R.id.cancel);
        Cancel.setVisibility(View.GONE);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtSearch.setText("");
                Cancel.setVisibility(View.GONE);
                Search.setVisibility(View.VISIBLE);

                Redo();

            }
        });

        Search = findViewById(R.id.search);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtSearch.getText().toString().equals("")){
                    filtrar(txtSearch.getText().toString());

                    Search.setVisibility(View.GONE);
                    Cancel.setVisibility(View.VISIBLE);

                }
            }
        });


        Ascending= findViewById(R.id.asce);
        Ascending.setVisibility(View.GONE);

        Ascending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip();
                Ascending.setVisibility(View.GONE);
                Descending.setVisibility(View.VISIBLE);


            }
        });

        Descending = findViewById(R.id.desc);

        Descending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip();
                Descending.setVisibility(View.GONE);
                Ascending.setVisibility(View.VISIBLE);
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

    private void flip(){
        ArrayList<QRlist> Asc = listadapter.GetList();
        Collections.reverse(Asc);
        listadapter = new ListAdapter(Asc, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date = date.get(recyclerView.getChildAdapterPosition(v)).getDate();
                SNDate = date.get(recyclerView.getChildAdapterPosition(v)).getDateSN();

                gu = new getuser();
                gu.start();
                gc = new getcount();
                gc.start();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AttendanceList.this, ListActivity.class );
                        startActivity(intent);
                        finish();

                    }
                }, 1000);

            }
        });
        recyclerView.setAdapter(listadapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener( ));

    }
    private void Redo() {
        listadapter = new ListAdapter(DateList.DL, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        listadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date = date.get(recyclerView.getChildAdapterPosition(v)).getDate();
                SNDate = date.get(recyclerView.getChildAdapterPosition(v)).getDateSN();

                gu = new getuser();
                gu.start();
                gc = new getcount();
                gc.start();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AttendanceList.this, ListActivity.class );
                        startActivity(intent);
                        finish();

                    }
                }, 1000);

            }
        });
        recyclerView.setAdapter(listadapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener( ));
    }

    private void filtrar(String filtrar) {

        ArrayList<QRlist> CloneList = new ArrayList<QRlist>();
        for (QRlist qr : date) {
            if(qr.getDate().contains(filtrar)){
                CloneList.add(qr);
            }
        }

        listadapter = new ListAdapter(CloneList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date = date.get(recyclerView.getChildAdapterPosition(v)).getDate();
                SNDate = date.get(recyclerView.getChildAdapterPosition(v)).getDateSN();

                gu = new getuser();
                gu.start();
                gc = new getcount();
                gc.start();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AttendanceList.this, ListActivity.class );
                        startActivity(intent);
                        finish();

                    }
                }, 1000);

            }
        });
        recyclerView.setAdapter(listadapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener( ));

    }

    public void onBackPressed(){
        super.onBackPressed();
        atras();

    }

    public void atras(){
        Intent intent = new Intent(AttendanceList.this, GroupActivity.class);
        startActivity(intent);
        finish();
    }
}