package com.example.h260210.room_viewmodel_rx_main.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.h260210.room_viewmodel_rx_main.R;
import com.example.h260210.room_viewmodel_rx_main.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<User> mUserList;

    public UserAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mLayoutInflater.inflate(R.layout.list_users, viewGroup, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
        User user = mUserList.get(position);
        userViewHolder.userId.setText(String.valueOf(user.getUserId()));
        userViewHolder.username.setText(user.getUsername());
        userViewHolder.email.setText(user.getEmailId());
    }

    @Override
    public int getItemCount() {
        if (null != mUserList) {
            return mUserList.size();
        }
        return 0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userId;
        TextView username;
        TextView email;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.textView_userId);
            username = itemView.findViewById(R.id.textView_username);
            email = itemView.findViewById(R.id.textView_email);
        }
    }

    public void setUser(List<User> userList){
        mUserList = userList;
        notifyDataSetChanged();
    }
}
