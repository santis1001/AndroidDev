package com.example.intracer.Fr_groups;
import com.example.intracer.R;
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
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private Button Back;
    private TextView counter, title;
    private ProgressBar spinner;
    private int progress;
    private int count = getcount.userCount;
    private int total = getuser.UL.size();
    ArrayList<UserList> user = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        spinner = findViewById(R.id.progressBar);
        counter = findViewById(R.id.counter);
        title = findViewById(R.id.title);
        title.setText(AttendanceList.Date);

        getList();


        counter.setText(total+"/"+count);

        progress = (100/count)*total;
        spinner.setProgress(progress);


        UserAdapter listadapter = new UserAdapter(user, this);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(listadapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener( ));


        Back =  findViewById(R.id.back);
        Back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                atras();
            }
        });
    }

    private void getList() {
        //user.add(new UserList("santis1001@gmail.com", "2021-11-19 05:05"));
        for (UserList l:getuser.UL){
            user.add(new UserList(l.email,l.Datesc));

        }
    }




    public void onBackPressed(){
        super.onBackPressed();
        atras();

    }

    public void atras(){
        Intent intent = new Intent(ListActivity.this, AttendanceList.class);
        startActivity(intent);
        finish();
    }
}