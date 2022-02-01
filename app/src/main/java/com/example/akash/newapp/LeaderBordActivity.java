package com.example.akash.newapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.akash.newapp.Model.LeaderBordAdapter;
import com.example.akash.newapp.Model.User;
import com.example.akash.newapp.databinding.ActivityLeaderBordBinding;
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

        firestore.collection("USERS")
                .orderBy("coins", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                            User user = snapshot.toObject(User.class);
                            users.add(user);
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