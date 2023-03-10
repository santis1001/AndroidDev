package com.example.intracer.Fr_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.example.intracer.UserLogin.PasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {


    private ProfileVM profileview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileview =
                ViewModelProviders.of(this).get(ProfileVM.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        String name, email;
        name = MenuActivity.username;
        email = MenuActivity.email;


        TextView Name = root.findViewById(R.id.UserName);
        Name.setText(name);
        TextView Email = root.findViewById(R.id.UserEmail);

        Email.setText(email);

        Button Password = root.findViewById(R.id.password);
        Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmail(email);
            }
        });

        return root;
    }

    private void sendmail(String mail) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String EmailAdress = mail;

        auth.sendPasswordResetEmail(EmailAdress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),
                                    getText(R.string.Forgot_Toast), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),
                                    getText(R.string.Forgot_Toast_error), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
