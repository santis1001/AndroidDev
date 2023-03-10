package com.example.intracer.Fr_chat;
import com.example.intracer.Fr_groups.AttendanceList;
import com.example.intracer.Fr_groups.ListActivity;
import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ChatFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<ChatList> list = new ArrayList<>();
    public static ActualChat Ac;

    FloatingActionButton fab1;

    ChatAdapter listadapter;
    View view;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModelProviders.of(this).get(ChatVM.class);
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        getcontacts();

        fab1 = view.findViewById(R.id.fab);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), NewChatActivity.class));
            }
        });
        return view;
    }


    FirebaseAuth mb = FirebaseAuth.getInstance();
    FirebaseUser user =  mb.getCurrentUser();
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Contacts").child(user.getUid());
    String check[];
    public static String doc, src;
    public static int i,  cant, lenght;
    public  ArrayList<String> uid  ;
    public  ArrayList<String> mail ;
    public  ArrayList<String> name ;

    public static ArrayList<ChatList> Cont ;




    private void getcontacts() {

        db.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    uid = new ArrayList<>();
                    mail = new ArrayList<>();
                    name = new ArrayList<>();

                    src = snapshot.getValue().toString();
                    String contact[] = src.split("=");
                    if(contact.length>2) {
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

                                    String auxmail[] = aux[1].split("@");
                                    String email =aux[1];
                                    if (check_mail(email)) {

                                        DocumentReference docref = fs.collection("Users").document(email);
                                        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                            doc = document.getData().get("Name").toString();

                                                            uid.add(aux[0]);
                                                            mail.add(aux[1]);
                                                            name.add(doc);
                                                            System.out.println(aux[0] + " " + aux[1] + "" + doc);

                                                        if(uid.size()==name.size() && uid.size()==mail.size()){

                                                            System.out.println("====================="+(snapshot.getValue()));
                                                            System.out.println((snapshot.getValue()));
                                                            System.out.println("=>"+uid.size());

                                                            Cont = new ArrayList<>();
                                                            for(int i =0;i< uid.size();i++) {
                                                                System.out.println("=====================");
                                                                Cont.add(new ChatList(uid.get(i),name.get(i),  mail.get(i)));
                                                                System.out.println(uid.get(i)+" "+mail.get(i)+""+name.get(i));

                                                            }

                                                            listadapter = new ChatAdapter(Cont, getContext());

                                                            recyclerView = view.findViewById(R.id.recycleview);
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                                            listadapter.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {

                                                                    Ac = new ActualChat(Cont.get(recyclerView.getChildAdapterPosition(v)).getUserID(),
                                                                            Cont.get(recyclerView.getChildAdapterPosition(v)).getUsermail(),
                                                                            Cont.get(recyclerView.getChildAdapterPosition(v)).getUsername());


                                                                    chat c1 = new chat();
                                                                    c1.start();

                                                                    new Handler().postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Intent intent = new Intent(getContext(), ChatActivity.class);
                                                                            startActivity(intent);

                                                                        }
                                                                    }, 1000);;
                                                                }
                                                            });

                                                            recyclerView.setAdapter(listadapter);
                                                            recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener( ));

                                                        }

                                                    } else {
                                                        uid.add(aux[0]);
                                                        mail.add(aux[1]);
                                                        name.add(MenuActivity.username);
                                                        System.out.println(aux[0] + " " + aux[1] + "" + MenuActivity.username);

                                                        if(uid.size()==name.size() && uid.size()==mail.size()){

                                                            System.out.println("====================="+(snapshot.getValue()));
                                                            System.out.println((snapshot.getValue()));
                                                            System.out.println("=>"+uid.size());

                                                            Cont = new ArrayList<>();
                                                            for(int i =0;i< uid.size();i++) {
                                                                System.out.println("=====================");
                                                                Cont.add(new ChatList(uid.get(i),name.get(i),  mail.get(i)));
                                                                System.out.println(uid.get(i)+" "+mail.get(i)+""+name.get(i));

                                                            }

                                                            listadapter = new ChatAdapter(Cont, getContext());

                                                            recyclerView = view.findViewById(R.id.recycleview);
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                                            listadapter.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {

                                                                    Ac = new ActualChat(Cont.get(recyclerView.getChildAdapterPosition(v)).getUserID(),
                                                                            Cont.get(recyclerView.getChildAdapterPosition(v)).getUsermail(),
                                                                            Cont.get(recyclerView.getChildAdapterPosition(v)).getUsername());


                                                                    chat c1 = new chat();
                                                                    c1.start();

                                                                    new Handler().postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Intent intent = new Intent(getContext(), ChatActivity.class);
                                                                            startActivity(intent);

                                                                        }
                                                                    }, 1000);;
                                                                }
                                                            });

                                                            recyclerView.setAdapter(listadapter);
                                                            recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener( ));

                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                        }

                    }else{

                        check = new String[1];
                        check[0] = src;
                        cant = check.length;
                        lenght = check.length;
                        for (int i = 0; i < check.length; i++) {

                            check[i] = check[i].substring(1, check[i].length() - 1);

                            check[i] = check[i].substring(0, check[i].length());

                            String aux[] = check[i].split("=");

                            String email = aux[1];
                            if (check_mail(email)) {
                                uid.add(aux[0]);
                                mail.add(aux[1]);

                                DocumentReference docref = fs.collection("Users").document(email);
                                docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                doc = document.getData().get("Name").toString();

                                                name.add(doc);
                                                System.out.println("=>"+aux[0] + " " + aux[1] + " " + doc);
                                                System.out.println("==>"+uid.size()+ " " + mail.size() + " " + name.size());

                                                if (uid.size() == name.size() && uid.size() == mail.size()) {

//                            System.out.println("====================="+(snapshot.getValue()));
//                            System.out.println((snapshot.getValue()));
                                                    System.out.println("=>" + uid.size());

                                                    Cont = new ArrayList<>();
                                                    for (int e = 0; e < uid.size(); e++) {
                                                        System.out.println("=====================");
                                                        Cont.add(new ChatList(uid.get(e), name.get(e), mail.get(e)));
                                                        System.out.println(uid.get(e) + " " + mail.get(e) + " " + name.get(e));

                                                    }
                                                    if (getActivity() != null) {
                                                        listadapter = new ChatAdapter(Cont, getContext());

                                                        recyclerView = view.findViewById(R.id.recycleview);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                                        listadapter.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                                Ac = new ActualChat(Cont.get(recyclerView.getChildAdapterPosition(v)).getUserID(),
                                                                        Cont.get(recyclerView.getChildAdapterPosition(v)).getUsermail(),
                                                                        Cont.get(recyclerView.getChildAdapterPosition(v)).getUsername());


                                                                chat c1 = new chat();
                                                                c1.start();

                                                                new Handler().postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Intent intent = new Intent(getContext(), ChatActivity.class);
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

                                            } else {
                                                name.add(MenuActivity.username);
                                                if (uid.size() == name.size() && uid.size() == mail.size()) {

//                            System.out.println("====================="+(snapshot.getValue()));
//                            System.out.println((snapshot.getValue()));
                                                    System.out.println("=>" + uid.size());

                                                    Cont = new ArrayList<>();
                                                    for (int e = 0; e < uid.size(); e++) {
                                                        System.out.println("=====================");
                                                        Cont.add(new ChatList(uid.get(e), name.get(e), mail.get(e)));
                                                        System.out.println(uid.get(e) + " " + mail.get(e) + " " + name.get(e));

                                                    }
                                                    if (getActivity() != null) {
                                                        listadapter = new ChatAdapter(Cont, getContext());

                                                        recyclerView = view.findViewById(R.id.recycleview);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                                        listadapter.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                                Ac = new ActualChat(Cont.get(recyclerView.getChildAdapterPosition(v)).getUserID(),
                                                                        Cont.get(recyclerView.getChildAdapterPosition(v)).getUsermail(),
                                                                        Cont.get(recyclerView.getChildAdapterPosition(v)).getUsername());


                                                                chat c1 = new chat();
                                                                c1.start();

                                                                new Handler().postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Intent intent = new Intent(getContext(), ChatActivity.class);
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
                                    }
                                });
                            }


                            System.out.println("==>" + uid.size() + " " + mail.size() + " " + name.size());


                        }

                    }

                    }



                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean check_mail(String Cmail){

        String p = "src/main/res/"+Cmail+".txt";
        Path ruta = Paths.get(p);
        if(Files.exists(ruta)){
            return false;
        }else{
            return true;
        }
    }
    /*
    *


    * */
}
