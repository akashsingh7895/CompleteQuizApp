package com.avs.akashsingh.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import com.avs.akashsingh.newapp.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;

    private Animation topAnim,bottomAmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.no_internet_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

                 Button button= dialog.findViewById(R.id.btn);
                 button.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         recreate();
                     }
                 });

                 dialog.show();


        }else {



            topAnim = AnimationUtils.loadAnimation(this,R.anim.splash_top_anim);
            bottomAmin = AnimationUtils.loadAnimation(this,R.anim.splash_bottom_anim);

            binding.logoIv.setAnimation(topAnim);
            binding.titleTv.setAnimation(bottomAmin);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //todo: check if user is already is login

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    if (firebaseAuth.getCurrentUser() ==null){
                        Intent mainIntent = new Intent(SplashActivity.this,SignUpActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }
            },3000);



        }


    }
}