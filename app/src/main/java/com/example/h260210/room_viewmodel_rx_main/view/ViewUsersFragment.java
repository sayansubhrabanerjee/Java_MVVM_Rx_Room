package com.example.h260210.room_viewmodel_rx_main.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.h260210.room_viewmodel_rx_main.R;
import com.example.h260210.room_viewmodel_rx_main.model.User;
import com.example.h260210.room_viewmodel_rx_main.view.adapter.UserAdapter;
import com.example.h260210.room_viewmodel_rx_main.viewmodel.UserViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewUsersFragment extends Fragment {

    private Context mContext;

    private UserViewModel mUserViewModel;

    private RecyclerView mRecyclerViewUser;
    private UserAdapter mUserAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    public ViewUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_users, container, false);
        initViews(view);
        displayAllUsers();
        return view;
    }

    private void initViews(View view){
        mRecyclerViewUser = view.findViewById(R.id.recycler_view_user);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewUser.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        mUserAdapter = new UserAdapter(mContext);
        mRecyclerViewUser.setAdapter(mUserAdapter);
        mRecyclerViewUser.setLayoutManager(mLinearLayoutManager);
    }

    private void displayAllUsers(){
        mUserViewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                mUserAdapter.setUser(users);
            }
        });
    }

}
