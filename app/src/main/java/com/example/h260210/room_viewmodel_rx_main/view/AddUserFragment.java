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
import com.example.h260210.room_viewmodel_rx_main.model.IAddUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.ISearchUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.User;
import com.example.h260210.room_viewmodel_rx_main.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment implements View.OnClickListener,
        ISearchUserDBListener, IAddUserDBListener {

    private static final String TAG = AddUserFragment.class.getSimpleName();

    private UserViewModel mUserViewModel;

    private Context mContext;

    private EditText mAddUserPageUserIdEdtTxt;
    private EditText mAddUserPageUsernameEdtTxt;
    private EditText mAddUserPageEmailEdtTxt;
    private Button mAddUserPageSaveBtn;

    private int mUserId;
    private String mUsername;
    private String mEmail;

    public AddUserFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mAddUserPageUserIdEdtTxt = view.findViewById(R.id.editText_add_user_page_user_id);
        mAddUserPageUsernameEdtTxt = view.findViewById(R.id.editText_add_user_page_user_name);
        mAddUserPageEmailEdtTxt = view.findViewById(R.id.editText_add_user_page_email);
        mAddUserPageSaveBtn = view.findViewById(R.id.button_add_user_page_save);

        mAddUserPageSaveBtn.setOnClickListener(this);
    }

    private boolean ifFieldsLeftBlank(){
        return TextUtils.isEmpty(mAddUserPageUserIdEdtTxt.getText().toString())
                || TextUtils.isEmpty(mAddUserPageUsernameEdtTxt.getText().toString())
                || TextUtils.isEmpty(mAddUserPageEmailEdtTxt.getText().toString());
    }

    private void checkIfUsernameAvailable(String username){
        mUserViewModel.getUserByUsername(username, this);
    }

    private void verifyUser(){
        if (ifFieldsLeftBlank()){
            Toast.makeText(mContext, "Fields can't be left blank!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            mUserId = Integer.parseInt(mAddUserPageUserIdEdtTxt.getText().toString().trim());
            mUsername = mAddUserPageUsernameEdtTxt.getText().toString().trim();
            mEmail = mAddUserPageEmailEdtTxt.getText().toString().trim();

            checkIfUsernameAvailable(mUsername);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_add_user_page_save:
                verifyUser();
                break;
        }
    }

    @Override
    public void onUserFound(User user) {
        Toast.makeText(mContext, user.getUsername()+" already exists!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotFound() {
        User user = new User();
        user.setUserId(mUserId);
        user.setUsername(mUsername);
        user.setEmailId(mEmail);

        if (null != mUserViewModel) {
            mUserViewModel.addUser(user, this);
        }
    }

    @Override
    public void onSearchUserError(Throwable e) {
        Log.d(TAG,"mytest: onSearchUserError: "+e.getMessage());
    }

    @Override
    public void onUserAdded(User user) {
        Toast.makeText(mContext, user.getUsername()+" added successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddUserError(Throwable e) {
        Log.d(TAG,"mytest: onAddUserError: "+e.getMessage());
    }
}
