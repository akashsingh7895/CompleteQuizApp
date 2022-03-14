package com.avs.akashsingh.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.avs.akashsingh.newapp.databinding.ActivityWatchVideoBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WatchVideoActivity extends AppCompatActivity {

    ActivityWatchVideoBinding binding;
    long coins= 0;
    FirebaseFirestore firestore;
    private RewardedAd mRewardedAd;
    private AdView mAdView;

    public static Dialog loadingDialog,loadingDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.banner_ads));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ///loading Dialog
        loadingDialog1 = new Dialog(WatchVideoActivity.this);
        loadingDialog1.setContentView(R.layout.loading_progress_dialog);
        loadingDialog1.setCancelable(false);
        loadingDialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog1.show();
        /////end loading dialog


        loadAd1();
        loadAd2();
        loadAd3();
        loadAd4();
        loadAd5();


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // fetch coins

        // coins fetch
        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()){
                            loadingDialog1.dismiss();
                            DocumentSnapshot snapshot = task.getResult();
                            coins = (long) snapshot.get("coins");  // error line

                            binding.coinsCoins.setText(String.valueOf(coins));
                           // loadingDialog1.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        //   coins fetch finish

        binding.video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd != null) {

                    Activity activityContext = WatchVideoActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd1();
                            coins = coins + 10;

                            firestore.collection("USERS")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("coins",coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        firestore.collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()){

                                                    DocumentSnapshot snapshot = task.getResult();
                                                    coins = (long) snapshot.get("coins");  // error line

                                                    binding.coinsCoins.setText(String.valueOf(coins));
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            binding.video1Image.setImageResource(R.drawable.check);
                        }
                    });

                } else {

                    Toast.makeText(getApplicationContext(), "Video is not ready please wait !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd != null) {
                    Activity activityContext = WatchVideoActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd2();
                            coins = coins + 20;

                            firestore.collection("USERS")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("coins",coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        firestore.collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()){

                                                    DocumentSnapshot snapshot = task.getResult();
                                                    coins = (long) snapshot.get("coins");  // error line

                                                    binding.coinsCoins.setText(String.valueOf(coins));
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            binding.video2Image.setImageResource(R.drawable.check);
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Video is not ready please wait !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd != null) {
                    Activity activityContext = WatchVideoActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd3();
                            coins = coins + 30;

                            firestore.collection("USERS")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("coins",coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        firestore.collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()){

                                                    DocumentSnapshot snapshot = task.getResult();
                                                    coins = (long) snapshot.get("coins");  // error line

                                                    binding.coinsCoins.setText(String.valueOf(coins));
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            binding.video3Image.setImageResource(R.drawable.check);
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Video is not ready please wait !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.video4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd != null) {
                    Activity activityContext = WatchVideoActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd4();
                            coins = coins + 40;

                            firestore.collection("USERS")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("coins",coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        firestore.collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()){

                                                    DocumentSnapshot snapshot = task.getResult();
                                                    coins = (long) snapshot.get("coins");  // error line

                                                    binding.coinsCoins.setText(String.valueOf(coins));
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            binding.video4Image.setImageResource(R.drawable.check);
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Video is not ready please wait !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.video5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd != null) {
                    Activity activityContext = WatchVideoActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd5();
                            coins = coins + 50;

                            firestore.collection("USERS")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("coins",coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        firestore.collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()){

                                                    DocumentSnapshot snapshot = task.getResult();
                                                    coins = (long) snapshot.get("coins");  // error line

                                                    binding.coinsCoins.setText(String.valueOf(coins));
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            binding.video5Image.setImageResource(R.drawable.check);
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Video is not ready please wait !", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    void loadAd1() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, getString(R.string.reward_video_ad1),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.

                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });

    }
    void loadAd2() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, getString(R.string.reward_video_ad2),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.

                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });

    }
    void loadAd3() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, getString(R.string.reward_video_ad3),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.

                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });

    }
    void loadAd4() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, getString(R.string.reward_video_ad4),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.

                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });

    }
    void loadAd5() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, getString(R.string.reward_video_ad5),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.

                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });

    }

}