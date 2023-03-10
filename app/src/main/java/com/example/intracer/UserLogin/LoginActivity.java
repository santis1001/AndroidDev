package com.example.intracer.UserLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intracer.MainActivity;
import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel, olvidasteC;
    ImageView signUpImageView, LangIM;
    TextInputLayout usuarioSignUpTF, ContraTF;
    MaterialButton InicioSesion;

    TextInputEditText email_ET, pass_ET;
    private FirebaseAuth mAuth;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_login);

        signUpImageView = findViewById(R.id.imagen_icono);
        bienvenidoLabel =  findViewById(R.id.Label_1);
        continuarLabel =  findViewById(R.id.Label_2);
        usuarioSignUpTF =  findViewById(R.id.email_Usuario);
        ContraTF =  findViewById(R.id.contraTF);
        InicioSesion =  findViewById(R.id.InicioSesion);
        olvidasteC =  findViewById(R.id.olvidasteC);
        nuevoUsuario =  findViewById(R.id.NuevoUsuario);

        olvidasteC =  findViewById(R.id.olvidasteC);
        email_ET =  findViewById(R.id.emailET);
        pass_ET =  findViewById(R.id.Pass_ET);

        LangIM =   findViewById(R.id.lang_ic);

        mAuth = FirebaseAuth.getInstance();

        nuevoUsuario.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signup();
            }
        });
        InicioSesion.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                validate();
            }
        });

        olvidasteC.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fogot_password();
            }
        });
        LangIM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                C_Lang();
            }
        });


    }

    public void validate() {

        String email = email_ET.getText().toString().trim();
        String pass = pass_ET.getText().toString().trim();


        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_ET.setError(getText(R.string.Toast_Error_correo));
        }else {
            email_ET.setError(null);

        }

        if(pass.isEmpty() || pass.length() < 8){
            pass_ET.setError(getText(R.string.Toast_Error_password));

        }else{
            IniciarSesion(email, pass);
            return;
        }


    }

    public void IniciarSesion(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user =mAuth.getCurrentUser();
                            if(!user.isEmailVerified()) {
                                Toast.makeText(LoginActivity.this,
                                        getText(R.string.Toast_Check_Verif), Toast.LENGTH_LONG).show();

                            }else{
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, getText(R.string.Toast_Error_incioS), Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    public void signup(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void fogot_password() {
        Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
        startActivity(intent);
        finish();
    }

    private void C_Lang() {
        final String[] items = {getText(R.string.ingles).toString(),getText(R.string.espanol).toString()};
        AlertDialog.Builder mb = new AlertDialog.Builder(LoginActivity.this);

        mb.setTitle(getText(R.string.Sel_title).toString());
        mb.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int sel) {
                if(sel == 0){
                    setLocale("en");
                    recreate();
                }
                else if(sel == 1){
                    setLocale("es");
                    recreate();
                }
                dialog.dismiss();

            }
        });
        AlertDialog mdial = mb.create();
        mdial.show();

    }

    private void setLocale(String s) {
        Locale loc = new Locale(s);
        Locale.setDefault(loc);
        Configuration conf = new Configuration();
        conf.locale = loc;
        getBaseContext().getResources().updateConfiguration(
                conf, getBaseContext().getResources().getDisplayMetrics());


        //SharedPreferences Pref = getApplicationContext().getSharedPreferences("UserLang", 0);

        SharedPreferences Pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = Pref.edit();
        edit.clear();

        edit.putString("My_lang", s).apply();

        String lang = Pref.getString("My_Lang","");
        System.out.println("es consola apply "+lang);



    }
    private void loadLocale() {
//
//        SharedPreferences Pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        String lang = Pref.getString("My_Lang","");
//        System.out.println("es consola "+lang);
//
//        Locale loc = new Locale(lang);
//        Locale.setDefault(loc);
//        Configuration conf = new Configuration();
//        conf.locale = loc;
//        getBaseContext().getResources().updateConfiguration(
//                conf, getBaseContext().getResources().getDisplayMetrics());
//
//
    }

}