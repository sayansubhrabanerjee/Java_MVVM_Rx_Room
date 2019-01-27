package com.example.h260210.room_viewmodel_rx_main.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.h260210.room_viewmodel_rx_main.R;
import com.example.h260210.room_viewmodel_rx_main.model.ISearchUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.IUpdateUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.User;
import com.example.h260210.room_viewmodel_rx_main.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserFragment extends Fragment implements View.OnClickListener,
        ISearchUserDBListener, IUpdateUserDBListener {

    private static final String TAG = UpdateUserFragment.class.getSimpleName();
    private Context mContext;

    private UserViewModel mUserViewModel;

    private EditText mUpdateUserPageUserIdEdtTxt;
    private EditText mUpdateUserPageUsernameEdtTxt;
    private EditText mUpdateUserPageEmailEdtTxt;
    private Button mUpdateUserPageUpdateBtn;

    private int mUserId;
    private String mUsername;
    private String mEmail;


    public UpdateUserFragment() {
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
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mUpdateUserPageUserIdEdtTxt = view.findViewById(R.id.editText_update_user_page_user_id);
        mUpdateUserPageUsernameEdtTxt = view.findViewById(R.id.editText_update_user_page_user_name);
        mUpdateUserPageEmailEdtTxt = view.findViewById(R.id.editText_update_user_page_email);
        mUpdateUserPageUpdateBtn = view.findViewById(R.id.button_update_user_page_update);

        mUpdateUserPageUpdateBtn.setOnClickListener(this);
    }

    private boolean ifFieldsLeftBlank(){
        return TextUtils.isEmpty(mUpdateUserPageUserIdEdtTxt.getText().toString())
                || TextUtils.isEmpty(mUpdateUserPageUsernameEdtTxt.getText().toString())
                || TextUtils.isEmpty(mUpdateUserPageEmailEdtTxt.getText().toString());
    }

    private void verifyUser(){
        if (ifFieldsLeftBlank()){
            Toast.makeText(mContext, "Fields can't be left blank!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            mUserId = Integer.parseInt(mUpdateUserPageUserIdEdtTxt.getText().toString());
            mUsername = mUpdateUserPageUsernameEdtTxt.getText().toString();
            mEmail = mUpdateUserPageEmailEdtTxt.getText().toString();

            checkIfUserExists(mUsername);
        }
    }

    private void checkIfUserExists(String username){
        mUserViewModel.getUserByUsername(username,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_update_user_page_update:
                verifyUser();
                break;
        }
    }

    @Override
    public void onUserFound(User user) {
        user.setUserId(mUserId);
        user.setUsername(mUsername);
        user.setEmailId(mEmail);

        if (null != mUserViewModel) {
            mUserViewModel.updateUser(user, this);
        }
    }

    @Override
    public void onUserNotFound() {
        Toast.makeText(mContext, "User not found!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchUserError(Throwable e) {
        Log.d(TAG,"mytest: onSearchUserError: "+e.getMessage());
    }

    @Override
    public void onUserUpdated(User user) {
        Toast.makeText(mContext, user.getUsername()+" updated successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserError(Throwable e) {
        Log.d(TAG,"mytest: onUpdateUserError: "+e.getMessage());

    }
}
