package com.example.intracer.UserLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intracer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView signUpImageView;
    TextInputLayout usuarioSignUpTF, ContraTF;
    MaterialButton InicioSesion;
    TextInputEditText email_ET, pass_ET, confpass_ET, nombre_ET;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        signUpImageView = findViewById(R.id.imagen_icono);
        bienvenidoLabel =  findViewById(R.id.Label_1);
        continuarLabel =  findViewById(R.id.Label_2);
        usuarioSignUpTF =  findViewById(R.id.email_Usuario);
        ContraTF =  findViewById(R.id.contraTF);
        InicioSesion=  findViewById(R.id.GuardarUsuario);
        nuevoUsuario =  findViewById(R.id.NuevoUsuario);
        email_ET =  findViewById(R.id.emailET);
        pass_ET =  findViewById(R.id.ContraET);
        nombre_ET = findViewById(R.id.nombre_ET);
        confpass_ET =  findViewById(R.id.confirma_contraET);


        nuevoUsuario.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                atras();
            }
        });

        InicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validar();
            }
        });


    }

    public void Validar() {
        String email = email_ET.getText().toString().trim();
        String pass = pass_ET.getText().toString().trim();
        String nomb = nombre_ET.getText().toString().trim();
        String confpass = confpass_ET.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_ET.setError(getText(R.string.Toast_Error_correo));
        }else {
            email_ET.setError(null);
        }

        if(pass.isEmpty() || pass.length() < 8){
            pass_ET.setError(getText(R.string.Toast_Error_correo));
            return;
        }else if(!Pattern.compile("[0-9]").matcher(pass).find()){
            pass_ET.setError(getText(R.string.Toast_Error_password_Num));
            return;
        } else{
            pass_ET.setError(null);
        }

        if(!confpass.equals(pass)){
            confpass_ET.setError(getText(R.string.Toast_Error_password_Equal));
            return;
        } else {
            registrar(email, pass, nomb);
        }

    }

    public void registrar(String email, String password, String nombre){
        Map<String, Object> userdb = new HashMap<>();

        Object perfil;



        userdb.put("Mail", email);
        userdb.put("Name", nombre);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification();
                            String uid = user.getUid();
                            userdb.put("UID", uid);

                            db.collection("User_"+email).document(email).set(userdb);
                            db.collection("Users").document(email).set(userdb);

                            Toast.makeText(SignUpActivity.this,
                                    R.string.Toast_Check_regist, Toast.LENGTH_LONG).show();


                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else{
                            Toast.makeText(SignUpActivity.this,
                                    R.string.Toast_Error_Verif, Toast.LENGTH_LONG).show();
                        }
                    }
        }       );
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        atras();
    }

    public void atras(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}