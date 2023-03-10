package com.example.intracer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.intracer.Fr_chat.NewChatActivity;
import com.example.intracer.UserLogin.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity{

    private static final float END_SCALE = 0.85f;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;
    private CoordinatorLayout contentView;

    private FirebaseFirestore fsdb;
    public static String username = " ";
    public static String email = "";
    TextView un ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initToolbar();
        initNavigation();
        ShowUsernam();
        //showBottomNavigation(false);
    }

    private void ShowUsernam() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        un = headerView.findViewById(R.id.LN_name);

        fsdb = FirebaseFirestore.getInstance();

        FirebaseAuth mb = FirebaseAuth.getInstance();
        FirebaseUser user =  mb.getCurrentUser();

        email = user.getEmail();



        DocumentReference docref = fsdb.collection("Users").document(email);
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        String name = document.getData().get("Name").toString();
                        username = name;

                        un.setText(username);
                    }
                    else {
                        Toast.makeText(MenuActivity.this,"error", Toast.LENGTH_LONG).show();
                        System.out.println("--------------------------");
                        System.out.println("No jala");
                        System.out.println("--------------------------");
                    }
                } else {
                    Toast.makeText(MenuActivity.this,"error", Toast.LENGTH_LONG).show();
                    System.out.println("--------------------------");
                    System.out.println("No jala");
                    System.out.println("--------------------------");
                }
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNavigation() {

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_language, R.id.nav_logout,
                R.id.bottom_chat, R.id.bottom_groups)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);

       // animateNavigationDrawer();


    }


//    private void animateNavigationDrawer() {
////        drawerLayout.setScrimColor(getResources().getColor(R.color.text_brown));
//        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//                // Scale the View based on current slide offset
//                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
//                final float offsetScale = 1 - diffScaledOffset;
//                contentView.setScaleX(offsetScale);
//                contentView.setScaleY(offsetScale);
//
//                // Translate the View, accounting for the scaled width
//                final float xOffset = drawerView.getWidth() * slideOffset;
//                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
//                final float xTranslation = xOffset - xOffsetDiff;
//                contentView.setTranslationX(xTranslation);
//            }
//        });
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}