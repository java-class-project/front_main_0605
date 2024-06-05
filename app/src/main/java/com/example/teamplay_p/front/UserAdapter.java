// UserAdapter.java
package com.example.teamplay_p.front;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamplay_p.R;
import com.example.teamplay_p.dto.user.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<UserList> userListArrayList;

    public UserAdapter(Context context, ArrayList<UserList> userListArrayList) {
        this.context = context;
        this.userListArrayList = userListArrayList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserList userlist = userListArrayList.get(position);
        holder.profile_img.setImageResource(userlist.profile_img);
        holder.username.setText(userlist.Username);
        holder.usernumber.setText(userlist.Usernumber);
        holder.major1.setText(userlist.Major1);
        holder.major2.setText(userlist.Major2);
        holder.major3.setText(userlist.Major3);


    }

    @Override
    public int getItemCount() {
        return userListArrayList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_img;

        TextView username, usernumber, major1, major2, major3;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_img = itemView.findViewById(R.id.rv_profile);
            username = itemView.findViewById(R.id.rv_username);
            usernumber = itemView.findViewById(R.id.rv_ClassNum);
            major1 = itemView.findViewById(R.id.rv_major);
            major2 = itemView.findViewById(R.id.rv_major2);
            major3 = itemView.findViewById(R.id.rv_major3);



        }


    }
}