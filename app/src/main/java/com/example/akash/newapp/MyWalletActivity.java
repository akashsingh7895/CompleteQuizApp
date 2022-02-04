package com.example.akash.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.akash.newapp.Model.WithdrawRequest;
import com.example.akash.newapp.databinding.ActivityMyWalletBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MyWalletActivity extends AppCompatActivity {
    ActivityMyWalletBinding binding;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;

    double coins;
    String name;
    long coinsAmount;
    double coins2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.amount.setText("Rs."+"00");

        database.collection("USERS")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            coins = (long) snapshot.get("coins");
                            name = (String)snapshot.get("name");
                            coins2 = coins/100;
                            binding.textView3.setText(String.valueOf("Rs."+coins2+"/-"));
                        }

                    }
                });
        
        binding.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (!binding.withdrawMob.getText().toString().equals("")){

                        if (!binding.amount.getText().toString().equals("")){

                            if(coins > 30000) {
                                String uid = FirebaseAuth.getInstance().getUid();
                                String mobil =   binding.withdrawMob.getText().toString();
                                coinsAmount = Long.parseLong(binding.amount.getText().toString());
                                WithdrawRequest request = new WithdrawRequest(uid, mobil,name,coinsAmount );
                                database
                                        .collection("withdraws")
                                        .document(uid)
                                        .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        double finalCoins = coins2-coinsAmount;
                                       // binding.textView3.setText(String.valueOf("Rs."+finalCoins+"/-"));
                                        updateCoins(finalCoins);
                                        binding.withdrawMob.setText("");
                                        binding.amount.setText("");
                                        Toast.makeText(MyWalletActivity.this, "Request sent successfully.", Toast.LENGTH_SHORT).show();


                                    }
                                });
                            } else {

                                Dialog dialog = new Dialog(MyWalletActivity.this);
                                dialog.setContentView(R.layout.infcument_coins_diolag);
                                dialog.setCancelable(true);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                dialog.show();
                            }
                        }else {
                            binding.amount.setError("please enter coins");
                        }
                    }else {
                        binding.withdrawMob.setError("Please enter coins");
                    }

            }
        });


    }
    void updateCoins(double amt){
        database.collection("USERS")
                .document(firebaseAuth.getCurrentUser().getUid())
                .update("coins",FieldValue.increment(amt)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()){
                       binding.amount.setText(String.valueOf("Rs."+coins2+"/-"));
                   }else {
                       Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
            }
        });

    }
}