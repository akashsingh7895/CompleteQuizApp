package com.avs.akashsingh.newapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.avs.akashsingh.newapp.Model.LeaderBordAdapter;
import com.avs.akashsingh.newapp.Model.User;

import com.avs.akashsingh.newapp.databinding.ActivityLeaderBordBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaderBordActivity extends AppCompatActivity {

   private ActivityLeaderBordBinding binding;
    private FirebaseFirestore firestore;

     private ArrayList<User>users;

     public static Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityLeaderBordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        users = new ArrayList<>();

        binding.dddd.setLayoutManager(new LinearLayoutManager(this));
        LeaderBordAdapter adapter = new LeaderBordAdapter(this,users);
        binding.dddd.setAdapter(adapter);

        ///loading Dialog
        loadingDialog = new Dialog(LeaderBordActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////end loading dialog



        firestore.collection("USERS")
                .orderBy("coins", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                            User user = snapshot.toObject(User.class);
                            users.add(user);
                            loadingDialog.dismiss();
                        }
                        adapter.notifyDataSetChanged();

                    }
                });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}