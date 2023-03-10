package com.example.intracer.Fr_Lang;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
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

import com.example.intracer.Fr_Logout.LogoutVM;
import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.example.intracer.UserLogin.LoginActivity;

import java.util.Locale;

public class LanguageFragment extends Fragment {


    private LangVM langview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        langview =
//                ViewModelProviders.of(this).get(LangVM.class);
//        View root = inflater.inflate(R.layout.fragment_language, container, false);

        C_Lang();


        return null;
                        /*Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);*/
    }

    private void C_Lang() {

        final String[] items = {getText(R.string.ingles).toString(),getText(R.string.espanol).toString()};

        AlertDialog.Builder mb = new AlertDialog.Builder(getContext());

        mb.setTitle(getText(R.string.Sel_title).toString());
        mb.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int sel) {
                if(sel == 0){
                    setLocale("en");
                    Intent intent = new Intent(getActivity(), MenuActivity.class);
                    startActivity(intent);
                }
                else if(sel == 1){
                    setLocale("es");
                    Intent intent = new Intent(getActivity(), MenuActivity.class);
                    startActivity(intent);
                }
                dialog.dismiss();


            }
        }).setNegativeButton(R.string.PP_no,new DialogInterface.OnClickListener() {
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

    private void setLocale(String s) {
        Locale loc = new Locale(s);
        Locale.setDefault(loc);
        Configuration conf = new Configuration();
        conf.locale = loc;
        getContext().getResources().updateConfiguration(
                conf, getContext().getResources().getDisplayMetrics());


        //SharedPreferences Pref = getApplicationContext().getSharedPreferences("UserLang", 0);

        SharedPreferences Pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = Pref.edit();
        edit.clear();

        edit.putString("My_lang", s).apply();

        String lang = Pref.getString("My_Lang","");

    }

}