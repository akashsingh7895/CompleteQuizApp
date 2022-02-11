package com.example.akash.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

public class MyWalletActivity extends AppCompatActivity {
    ActivityMyWalletBinding binding;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;

    private long coins;
    private String name;
    private long coinsAmount;

    public static Dialog loadingDialog,loadingDialog1;


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

       // binding.amount.setText("00");

        ///loading Dialog
        loadingDialog1 = new Dialog(MyWalletActivity.this);
        loadingDialog1.setContentView(R.layout.loading_progress_dialog);
        loadingDialog1.setCancelable(false);
        loadingDialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog1.show();
        /////end loading dialog


        loadingDialog = new Dialog(MyWalletActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        // coins fetch
        database.collection("USERS")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            coins = (long) snapshot.get("coins");  // error line
                            name = (String)snapshot.get("name");
                            binding.textView3.setText(String.valueOf("Coins = "+coins));
                            loadingDialog1.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

      //   coins fetch finish

//        coins = getIntent().getLongExtra("user_coins",1);
//        name = getIntent().getStringExtra("user_name");

        binding.textView3.setText(String.valueOf(coins));
        binding.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!binding.withdrawMob.getText().toString().equals("")){

                        if (!binding.amount.getText().toString().equals("")){
                            loadingDialog.show();
                            binding.requestButton.setEnabled(true);
                            if(coins > 30000) {
                                String uid = FirebaseAuth.getInstance().getUid();
                                String mobil =   binding.withdrawMob.getText().toString();
                               // coinsAmount = Long.parseLong(binding.amount.getText().toString());
                                coinsAmount = Long.parseLong(binding.amount.getText().toString());

                                if (coins>=coinsAmount) {

                                    WithdrawRequest request = new WithdrawRequest(uid, mobil, name, coinsAmount);
                                    database
                                            .collection("withdraws")
                                            .document(uid)
                                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            long finalCoins = coins - coinsAmount;
                                            binding.textView3.setText(String.valueOf("Coins = " + finalCoins));

                                            Map<String, Object> updateUserData = new HashMap<>();
                                            updateUserData.put("coins", finalCoins);

                                            database.collection("USERS").document(firebaseAuth.getUid())
                                                    .update(updateUserData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // binding.textView3.setText(String.valueOf("Coins = "+finalCoins));

//                                                            Dialog dialog = new Dialog(MyWalletActivity.this);
//                                                            dialog.setContentView(R.layout.request_send_diloag);
//                                                            dialog.setCancelable(true);
//                                                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
//                                                            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
//                                                            dialog.show();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                            loadingDialog.dismiss();

                                            Dialog dialog = new Dialog(MyWalletActivity.this);
                                            dialog.setContentView(R.layout.request_send_diloag);
                                            dialog.setCancelable(true);
                                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                            dialog.show();
                                            binding.withdrawMob.setText("");
                                            binding.amount.setText("");
                                            Toast.makeText(MyWalletActivity.this, "Request sent successfully.", Toast.LENGTH_SHORT).show();
                                            binding.requestButton.setEnabled(true);


                                        }
                                    });

                                }else {

                                    loadingDialog.dismiss();
                                    Dialog dialog = new Dialog(MyWalletActivity.this);
                                    dialog.setContentView(R.layout.infcument_coins_diolag);
                                    dialog.setCancelable(true);
                                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                    dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                    dialog.show();
                                    binding.requestButton.setEnabled(true);
                                }


                            } else {
                                loadingDialog.dismiss();
                                Dialog dialog = new Dialog(MyWalletActivity.this);
                                dialog.setContentView(R.layout.infcument_coins_diolag);
                                dialog.setCancelable(true);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                dialog.show();
                                binding.requestButton.setEnabled(true);
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
//    void updateCoins(double amt){
//        database.collection("USERS")
//                .document(firebaseAuth.getCurrentUser().getUid())
//                .update("coins",FieldValue.increment(amt)).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                   if (task.isSuccessful()){
//                       binding.amount.setText(String.valueOf("Rs."+coins2+"/-"));
//                   }else {
//                       Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                   }
//            }
//        });
//
//    }
}