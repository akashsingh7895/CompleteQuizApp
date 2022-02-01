package com.example.akash.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.akash.newapp.SpinWheel.LuckyWheelView;
import com.example.akash.newapp.SpinWheel.model.LuckyItem;
import com.example.akash.newapp.databinding.ActivitySpinWheelBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinWheelActivity extends AppCompatActivity {
    ActivitySpinWheelBinding binding;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinWheelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<LuckyItem> data = new ArrayList<>();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.topText = "10";
        luckyItem1.secondaryText = "coins";
        luckyItem1.color = Color.parseColor("#6B1B6E");
        luckyItem1.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.topText = "20";
        luckyItem2.secondaryText = "coins";
        luckyItem2.color = Color.parseColor("#4ECF18");
        luckyItem2.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem2);


        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.topText = "30";
        luckyItem3.secondaryText = "coins";
        luckyItem3.color = Color.parseColor("#D35D0F");
        luckyItem3.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem3);

        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.topText = "40";
        luckyItem4.secondaryText = "coins";
        luckyItem4.color = Color.parseColor("#9FC812");
        luckyItem4.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem4);


        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.topText = "50";
        luckyItem5.secondaryText = "coins";
        luckyItem5.color = Color.parseColor("#10C1A7");
        luckyItem5.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.topText = "60";
        luckyItem6.secondaryText = "coins";
        luckyItem6.color = Color.parseColor("#EC0D0D");
        luckyItem6.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem6);


        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.topText = "70";
        luckyItem7.secondaryText = "coins";
        luckyItem7.color = Color.parseColor("#EC0DE8");
        luckyItem7.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.topText = "80";
        luckyItem8.secondaryText = "coins";
        luckyItem8.color = Color.parseColor("#0D23EC");
        luckyItem8.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem8);

        binding.wheelView.setData(data);
        binding.wheelView.setRound(5);


        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int randomNumber = r.nextInt(8  );

                binding.wheelView.startLuckyWheelWithTargetIndex(randomNumber);
            }
        });

        binding.wheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCase(index);
            }
        });

        dialog = new Dialog(SpinWheelActivity.this);
        dialog.setContentView(R.layout.coins_success_layout);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

        Button button1 = dialog.findViewById(R.id.retry);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpinWheelActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    void updateCase(int index){

        long cash = 0;
        switch (index){

            case 0:
                cash = 10;
                break;

            case 1:
                cash = 20;
                break;

            case 2:
                cash = 30;
                break;
            case 3:
                cash = 40;
                break;
            case 4:
                cash = 50;
                break;
            case 5:
                cash = 60;
                break;
            case 6:
                cash = 70;
                break;
            case 7:
                cash = 80;
                break;
        }



        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(cash))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.show();
//                            Toast.makeText(getApplicationContext(), "coins Added", Toast.LENGTH_SHORT).show();
//                            finish();

                        }else {
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}