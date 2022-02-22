package com.avs.akashsingh.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.avs.akashsingh.newapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseFirestore database;
    private FirebaseAuth firebaseAuth;

    private ActionBarDrawerToggle toggle;

    long coins;
    String name;
    public static Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        binding.toolbar.setTitle("Quick quiz");
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        ///loading Dialog
        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////end loading dialog

        binding.allcategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CategoryActivity.class));
            }
        });

        binding.myWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,MyWalletActivity.class));
                Intent intent = new Intent(MainActivity.this,MyWalletActivity.class);
//                intent.putExtra("user_coins",coins);
//                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });

        binding.myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });


        binding.leaderbord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LeaderBordActivity.class));
            }
        });

        binding.spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SpinWheelActivity.class));
            }
        });


        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.drawerOpen,R.string.drawerClose);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        binding. drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.setItemIconTintList(null);

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                  MenuItem menuItem;

                     @Override
                     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                         menuItem = item;


                         binding.drawerLayout.closeDrawer(GravityCompat.START);
                         binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                             @Override
                             public void onDrawerClosed(View drawerView) {
                                 super.onDrawerClosed(drawerView);

                                 switch (menuItem.getItemId()) {
                                     case R.id.myProfile:
                                         startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                                         break;
                                     case R.id.myWallet:
                                         startActivity(new Intent(MainActivity.this,MyWalletActivity.class));

                                         break;
                                     case R.id.shareThis:

                                         try {
                                             Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                             shareIntent.setType("text/plain");
                                             shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                             String shareMessage= "\nLet me recommend you this application\n\n";
                                             shareMessage = shareMessage + "https://play.google.com/store/apps/details?id = com.example.akash.newapp" + BuildConfig.APPLICATION_ID +"\n\n";
                                             shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                                             startActivity(Intent.createChooser(shareIntent, "choose one"));
                                         } catch(Exception e) {
                                             //e.toString();
                                         }

                                         break;
                                     case R.id.rateThisApp:

                                         try{
                                             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                                         }
                                         catch (ActivityNotFoundException e){
                                             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                                         }

                                         break;
                                     case R.id.contactUs:

                                         Intent i = new Intent(Intent.ACTION_SEND);
                                         i.setType("message/rfc822");
                                         i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.supported_email)});
                                         i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                                         i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                                         try {
                                             startActivity(Intent.createChooser(i, "Send mail..."));
                                         } catch (android.content.ActivityNotFoundException ex) {
                                             Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                         }

                                         break;
                                     case R.id.privacyPoliy:

                                         Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy)));
                                         startActivity(browserIntent);

                                         break;
                                     case R.id.termsCondi:
                                         Intent searchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.terms_condition)));
                                         startActivity(searchIntent);

                                         break;
                                     case R.id.logout:
                                         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                         firebaseAuth.signOut();
                                         startActivity(new Intent(MainActivity.this,LogInActivity.class));
                                 }
                                 binding.drawerLayout.removeDrawerListener(this);


                             }
                         });

                         return true;
                     }
                 });

    }

    @Override
    protected void onStart() {
        super.onStart();


        database.collection("USERS").document(firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if (task.isSuccessful()){
                          DocumentSnapshot snapshot = task.getResult();
                          name = (String)snapshot.get("name");
                          //coins = (long) snapshot.get("coins");
                          binding.helloReaders1.setText(name);
                          loadingDialog.dismiss();
                      }else {
                          Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }
               

            }
        });


    }
}