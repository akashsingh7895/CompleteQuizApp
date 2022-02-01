package com.example.akash.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.akash.newapp.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();


        binding.resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!binding.emailEt.getText().toString().equals("")){

                    binding.progressBar3.setVisibility(View.VISIBLE);
                    binding.resetPassBtn.setEnabled(false);
                    binding.resetPassBtn.setText("");
                    firebaseAuth.sendPasswordResetEmail(binding.emailEt.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(getApplicationContext(), "Email send SuccessFully", Toast.LENGTH_LONG).show();


                            }else {
                                Toast.makeText(getApplicationContext(), "SomeThing went wrong" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                            binding.progressBar3.setVisibility(View.INVISIBLE);
                            binding.resetPassBtn.setEnabled(true);
                            binding.resetPassBtn.setText("Reset Password");
                        }
                    });

                }else {
                    binding.emailEt.setError("Please Enter Email");
                }

            }
        });


        binding.goBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this,SignUpActivity.class));
            }
        });
    }
}