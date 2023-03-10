package com.example.intracer.Fr_groups;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intracer.Fr_chat.ActualChat;
import com.example.intracer.Fr_chat.ChatActivity;
import com.example.intracer.Fr_chat.ChatAdapter;
import com.example.intracer.Fr_chat.ChatList;
import com.example.intracer.Fr_chat.ChatVM;
import com.example.intracer.Fr_chat.Contactos;
import com.example.intracer.Fr_chat.NewChatActivity;
import com.example.intracer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<GroupList> list ;
    FloatingActionButton fab;
    public static ActualGroup AG;
    View view;
    GroupAdapter listadapter;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModelProviders.of(this).get(ChatVM.class);
        view = inflater.inflate(R.layout.fragment_chat, container, false);



        refresh();

        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), CreateActivity.class));
            }
        });

        return view;
    }
    int counter=0;
    FirebaseAuth mb = FirebaseAuth.getInstance();
    FirebaseUser user =  mb.getCurrentUser();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Chats").child(user.getUid()).child("Groups");

    private void refresh() {
        counter=0;
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

                    if (Gid.size() == Name.size() && Gid.size() == Desc.size()&& Gid.size() == User.size() ) {
                        OutList = new ArrayList<>();
                        for (int e = 0; e < Gid.size(); e++) {
                            OutList.add(new GroupList(Gid.get(e), Name.get(e), Desc.get(e), User.get(e)));
                        }
                        if (getActivity() != null) {


                        listadapter = new GroupAdapter(OutList, getContext());

                        recyclerView = view.findViewById(R.id.recycleview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);
                        listadapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                AG = new ActualGroup(
                                        OutList.get(recyclerView.getChildAdapterPosition(v)).getGroupId(),
                                        OutList.get(recyclerView.getChildAdapterPosition(v)).getGroupname(),
                                        OutList.get(recyclerView.getChildAdapterPosition(v)).getDescription(),
                                        OutList.get(recyclerView.getChildAdapterPosition(v)).getUser());

                                DateList dl = new DateList();
                                dl.start();

                                Getmsg gm = new Getmsg();
                                gm.start();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getContext(), GroupActivity.class);
                                        startActivity(intent);

                                    }
                                }, 1000);
                                ;

                            }
                        });
                        recyclerView.setAdapter(listadapter);
                        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getGrouplist() {
        list = Grupos.OutList;
    }

}
