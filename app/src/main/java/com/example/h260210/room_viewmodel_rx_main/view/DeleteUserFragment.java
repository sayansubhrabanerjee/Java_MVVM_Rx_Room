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
import com.example.h260210.room_viewmodel_rx_main.model.IDeleteUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.ISearchUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.User;
import com.example.h260210.room_viewmodel_rx_main.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteUserFragment extends Fragment implements View.OnClickListener,
        ISearchUserDBListener, IDeleteUserDBListener{

    private static final String TAG = DeleteUserFragment.class.getSimpleName();
    private Context mContext;

    private UserViewModel mUserViewModel;

    private EditText mDeleteUserPageUsernameEdtTxt;
    private Button mDeleteUserPageDeleteBtn;

    private String mUsername;


    public DeleteUserFragment() {
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
        View view = inflater.inflate(R.layout.fragment_delete_user, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mDeleteUserPageUsernameEdtTxt = view.findViewById(R.id.editText_delete_user_page_user_name);
        mDeleteUserPageDeleteBtn = view.findViewById(R.id.button_delete_user_page_delete);

        mDeleteUserPageDeleteBtn.setOnClickListener(this);
    }

    private boolean ifFieldsLeftBlank(){
        return TextUtils.isEmpty(mDeleteUserPageUsernameEdtTxt.getText().toString());
    }

    private void verifyUser(){
        if (ifFieldsLeftBlank()){
            Toast.makeText(mContext, "Username can't be left blank!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            mUsername = mDeleteUserPageUsernameEdtTxt.getText().toString();

            checkIfUserExists(mUsername);
        }
    }

    private void checkIfUserExists(String username){
        mUserViewModel.getUserByUsername(username,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_delete_user_page_delete:
                verifyUser();
                break;
        }
    }

    @Override
    public void onUserDeleted(User user) {
        Toast.makeText(mContext, user.getUsername()+" deleted successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteUserError(Throwable e) {
        Log.d(TAG,"mytest: onDeleteUserError: "+e.getMessage());
    }

    @Override
    public void onUserFound(User user) {
        mUserViewModel.deleteUser(user,this);
    }

    @Override
    public void onUserNotFound() {
        Toast.makeText(mContext, "User not found!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchUserError(Throwable e) {
        Log.d(TAG,"mytest: onSearchUserError: "+e.getMessage());
    }
}
