package com.example.intracer.UserLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intracer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    TextInputEditText email_ET;
    MaterialButton recuperar;
    TextView goback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        email_ET = findViewById(R.id.emailET);
        recuperar =  findViewById(R.id.Retrieve);
        goback =  findViewById(R.id.atras);


        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras();
            }
        });
    }

    public void validate() {
        String mail = email_ET.getText().toString().trim();

        if(mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email_ET.setError(getText(R.string.Toast_Error_correo));
        }else {
            sendmail(mail);
            Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }



    }

    private void sendmail(String mail) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String EmailAdress = mail;

        auth.sendPasswordResetEmail(EmailAdress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(PasswordActivity.this,
                                    getText(R.string.Forgot_Toast), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(PasswordActivity.this,
                                    getText(R.string.Forgot_Toast_error), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        atras();
    }
    public void atras(){
        Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}