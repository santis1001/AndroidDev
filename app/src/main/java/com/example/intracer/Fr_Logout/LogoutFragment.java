package com.example.intracer.Fr_Logout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.intracer.MenuActivity;
import com.example.intracer.UserLogin.LoginActivity;
import com.example.intracer.R;
import com.google.firebase.auth.FirebaseAuth;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LogoutFragment extends Fragment {


    private LogoutVM logoutview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        logout();
//        logoutview =
//                ViewModelProviders.of(this).get(LogoutVM.class);
//        View root = inflater.inflate(R.layout.fragment_logout, container, false);
//
//        final TextView textView = root.findViewById(R.id.text2);
//
//
//        logoutview.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
//        final Button logoutbutton = root.findViewById(R.id.button_logout);
//        logoutbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//
//            }
//        });
        return null;

    }
    private void logout() {

        AlertDialog.Builder mb = new AlertDialog.Builder(getContext());

        mb.setTitle(R.string.PP_Logout).toString();

        mb.setMessage(R.string.PP_Que).setPositiveButton(R.string.PP_yes,

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.PP_no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                startActivity(intent);
                            }
                        }
                ).setCancelable(false);
        AlertDialog mdial = mb.create();
        mdial.show();
    }
}