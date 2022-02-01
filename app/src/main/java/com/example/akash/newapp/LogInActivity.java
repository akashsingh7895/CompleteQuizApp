package com.example.akash.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.akash.newapp.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish();
        }

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.emailEt.getText().toString().equals("")){
                    if (!binding.passwordEt.getText().toString().equals("")){

                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.logInButton.setText("");
                        binding.logInButton.setEnabled(false);

                        String email= binding.emailEt.getText().toString();
                        String pass =  binding.passwordEt.getText().toString();

                        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){

                                    binding.progressBar.setVisibility(View.VISIBLE);
                                    binding.logInButton.setEnabled(false);
                                    binding.logInButton.setText("");

                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else {


                                    binding.logInButton.setEnabled(true);
                                    binding.logInButton.setText("Log In");
                                    binding.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });








                    }else {

                        binding.emailEt.setError("Please enter password");
                    }

                }else {

                    binding.emailEt.setError("Please enter email");
                }


            }
        });

        binding.SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
            }
        });

        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,ForgotPasswordActivity.class));
            }
        });




    }
}