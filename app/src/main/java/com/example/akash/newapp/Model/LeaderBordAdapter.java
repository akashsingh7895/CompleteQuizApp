package com.example.akash.newapp.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akash.newapp.R;
import com.example.akash.newapp.databinding.RowLearbordBinding;

import java.util.ArrayList;

public class LeaderBordAdapter extends RecyclerView.Adapter<LeaderBordAdapter.ViewHolder>{

    Context context;
    ArrayList<User> users;


    public LeaderBordAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_learbord,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);
        holder.binding.name.setText(user.getName());
        holder.binding.rank.setText(String.format("#%d",position+1));

        long coins = user.getCoins();
        long coinss = coins;

        holder.binding.coins.setText(String.valueOf( coinss+"/-"));



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RowLearbordBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           binding = RowLearbordBinding.bind(itemView);
        }
    }
}
