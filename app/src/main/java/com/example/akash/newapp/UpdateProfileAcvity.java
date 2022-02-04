package com.example.akash.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.akash.newapp.databinding.ActivityUpdateProfileAcvityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileAcvity extends AppCompatActivity {

    ActivityUpdateProfileAcvityBinding binding;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    public static Dialog loadingDialog;
    private String name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileAcvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Toolbar toolbar = findViewById(R.id.userInfoToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///loading Dialog
                loadingDialog = new Dialog(UpdateProfileAcvity.this);
                loadingDialog.setContentView(R.layout.loading_progress_dialog);
                loadingDialog.setCancelable(false);
                loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
                loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingDialog.show();
                /////end loading dialog

                if (TextUtils.isEmpty(binding.newPassword.getText())){
                    //user is not changing the password only updating the above details
                    Map<String, Object> updateUserData = new HashMap<>();
                    updateUserData.put("name",binding.name.getText().toString());
                    updateUserData.put("mobile",binding.phone.getText().toString());

                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                            .update(updateUserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateProfileAcvity.this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(UpdateProfileAcvity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });

                } else {
                    //user is changing the password
                    if (!TextUtils.isEmpty(binding.oldPassword.getText())) {
                        if (binding.newPassword.getText().toString().equals(binding.cnfNewPassword.getText().toString())) {
                            //update in authenticaiton
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            final String email = user.getEmail();

                            AuthCredential credential = EmailAuthProvider.getCredential(email, binding.oldPassword.getText().toString());

                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(binding.newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Update in databse
                                                    Map<String, Object> updateUserPass = new HashMap<>();
                                                    updateUserPass.put("name", binding.name.getText().toString());
                                                    updateUserPass.put("phone", binding.phone.getText().toString());
                                                    updateUserPass.put("password", binding.newPassword.getText().toString());

                                                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                                            .update(updateUserPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(UpdateProfileAcvity.this, "Password & Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
                                                                loadingDialog.dismiss();
                                                                finish();

                                                            } else {
                                                                Toast.makeText(UpdateProfileAcvity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                loadingDialog.dismiss();

                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(UpdateProfileAcvity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    loadingDialog.dismiss();

                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(UpdateProfileAcvity.this, "Authentication Failed: Wrong Credentials!", Toast.LENGTH_SHORT).show();
                                        loadingDialog.dismiss();

                                    }
                                }
                            });
                            //end update in authentication

                        } else {
                            Toast.makeText(UpdateProfileAcvity.this, "Please enter same Password in Confirm Password!", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();

                        }
                    }else {
                        Toast.makeText(UpdateProfileAcvity.this, "Please Enter Your Old Password!", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();

                    }
                }
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){

                    DocumentSnapshot shot = task.getResult();

                    name = (String) shot.get("name");
                    phone = (String) shot.get("mobile");
                    binding.name.setText(name);
                    binding.phone.setText(phone);

                }else {
                    Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}