package com.example.akash.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.akash.newapp.Model.User;
import com.example.akash.newapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore database;

    private String emailPattern ="[a-zA-Z0_9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        binding.fullNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.mobileEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                if (binding.emailEt.getText().toString().matches(emailPattern)){
//                    if (binding.passwordEt.getText().toString().equals(binding.confirmPassword.getText().toString())){
//                        // ready to go auth
//
//                        binding.progressBar.setVisibility(View.VISIBLE);
//                        binding.signUpButton.setText("");
//                        binding.signUpButton.setEnabled(false);
//
//                        String email = binding.emailEt.getText().toString();
//                        String pass = binding.passwordEt.getText().toString();
//
//
//                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//
//
//                                if (task.isSuccessful()){
//
//                                    binding.progressBar.setVisibility(View.VISIBLE);
//                                    binding.signUpButton.setText("");
//                                    binding.signUpButton.setEnabled(false);
//
//
//                                    Map<String,Object> basicDetail = new HashMap<>();
//
//                                    basicDetail.put("full_name",binding.fullNameEt.getText().toString());
//                                    basicDetail.put("mobile_no",binding.mobileEt.getText().toString());
//                                    basicDetail.put("email",binding.emailEt.getText().toString());
//                                    basicDetail.put("coins",1000);
//
//
//                                    database.collection("USERS").document(firebaseAuth.getUid())
//                                            .set(basicDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                            if (task.isSuccessful()){
//
//                                                binding.progressBar.setVisibility(View.VISIBLE);
//                                                binding.signUpButton.setEnabled(false);
//                                                binding.signUpButton.setText("");
//                                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
//                                                finish();
//                                            }else {
//                                                binding.signUpButton.setEnabled(true);
//                                                binding.signUpButton.setText("Sign Up");
//                                                binding.progressBar.setVisibility(View.INVISIBLE);
//                                            }
//
//                                        }
//                                    });
//
//                                }else {
//
//                                    binding.signUpButton.setEnabled(true);
//                                    binding.signUpButton.setText("Sign Up");
//                                    binding.progressBar.setVisibility(View.INVISIBLE);
//
//                                }
//
//                            }
//                        });
//
//                    }else {
//                        binding.confirmPassword.setError("Password does not matches");
//                    }
//                }else {
//                    binding.emailEt.setError("please enter to valid Email address");
//                }
//
//            }
//        });





        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.emailEt.getText().toString().matches(emailPattern)){
                    if (binding.passwordEt.getText().toString().equals(binding.confirmPassword.getText().toString())){
                        // ready to go auth

                        binding.signUpButton.setEnabled(false);
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.signUpButton.setText("");

                        String email = binding.emailEt.getText().toString();
                        String password = binding.passwordEt.getText().toString();
                        String name = binding.fullNameEt.getText().toString();
                        String mobile = binding.mobileEt.getText().toString();

                        User user = new User(name,email,mobile);
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){

                                    String Uid = task.getResult().getUser().getUid();

                                    database.collection("USERS")
                                            .document(Uid)
                                            .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                startActivity(intent);
                                             finish();
                                            }else {
                                                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                                                binding.signUpButton.setEnabled(true);
                                                binding. progressBar.setVisibility(View.INVISIBLE);
                                                binding.signUpButton.setText("Sign Up");

                                            }

                                        }
                                    });
                                }else {
                                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

                                    binding.signUpButton.setEnabled(true);
                                    binding.progressBar.setVisibility(View.INVISIBLE);
                                    binding.signUpButton.setText("Sign Up");

                                }

                            }
                        });

                    }else {
                        binding. confirmPassword.setError("Password does not matches");
                    }
                }else {
                    binding. emailEt.setError("please enter to valid Email address");
                }






            }
        });










        binding.logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LogInActivity.class));
            }
        });



    }

    private void checkInput(){

        if (!binding.fullNameEt.getText().toString().equals("")){
            //full name has been filled till now
            if (!binding.emailEt.getText().toString().equals("")){

                if (!binding.mobileEt.getText().toString().equals("")){

                    if ((!binding.passwordEt.getText().toString().equals("")) && (binding.passwordEt.getText().toString().length()>=6)){

                        if (!binding.confirmPassword.getText().toString().equals("")){


                            binding.signUpButton.setEnabled(true);

                        }else {
                            binding.signUpButton.setEnabled(false);
                            binding.confirmPassword.setError("Please Enter confirm password");
                        }

                    }else {
                        binding.signUpButton.setEnabled(false);
                        binding.passwordEt.setError("Password must be a at least 6 character!");
                    }

                }else {
                    binding.signUpButton.setEnabled(false);
                    binding.mobileEt.setError("Please Enter Phone No...");
                }

            }else {
                binding.signUpButton.setEnabled(false);
                binding.emailEt.setError("Please Enter Email address");
            }

        }else {
            binding.signUpButton.setEnabled(false);
            binding.fullNameEt.setError("Please Enter full name");
        }

    }
}