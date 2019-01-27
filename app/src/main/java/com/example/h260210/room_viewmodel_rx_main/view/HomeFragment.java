package com.example.h260210.room_viewmodel_rx_main.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.h260210.room_viewmodel_rx_main.R;
import com.example.h260210.room_viewmodel_rx_main.model.IDeleteAllUsersDBListener;
import com.example.h260210.room_viewmodel_rx_main.viewmodel.UserViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener,
        IDeleteAllUsersDBListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private Context mContext;

    private Button mHomeAddUserBtn;
    private Button mHomeViewUsersBtn;
    private Button mHomeUpdateUserBtn;
    private Button mHomeDeleteUserBtn;
    private Button mHomeDeleteAllUsersBtn;

    private UserViewModel mUserViewModel;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mHomeAddUserBtn = view.findViewById(R.id.button_home_add_user);
        mHomeViewUsersBtn = view.findViewById(R.id.button_home_view_users);
        mHomeUpdateUserBtn = view.findViewById(R.id.button_home_update_user);
        mHomeDeleteUserBtn = view.findViewById(R.id.button_home_delete_user);
        mHomeDeleteAllUsersBtn = view.findViewById(R.id.button_home_delete_all_users);

        mHomeAddUserBtn.setOnClickListener(this);
        mHomeViewUsersBtn.setOnClickListener(this);
        mHomeUpdateUserBtn.setOnClickListener(this);
        mHomeDeleteUserBtn.setOnClickListener(this);
        mHomeDeleteAllUsersBtn.setOnClickListener(this);
    }

    private void replaceWithAddUserFragment(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, new AddUserFragment())
                .addToBackStack(null)
                .commit();
    }

    private void replaceWithViewUsersFragment(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, new ViewUsersFragment())
                .addToBackStack(null)
                .commit();
    }

    private void replaceWithUpdateUserFragment(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, new UpdateUserFragment())
                .addToBackStack(null)
                .commit();
    }

    private void replaceWithDeleteUserFragment(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, new DeleteUserFragment())
                .addToBackStack(null)
                .commit();
    }

    private void displayDeleteAllUsersAlertDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle("Delete All Users")
                .setMessage("Do you really want to delete all users?")
                .setPositiveButton("DELETE ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteAllUsers();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        alertDialog.show();
    }

    private void deleteAllUsers(){
        mUserViewModel.deleteAllUsers(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_home_add_user:
                replaceWithAddUserFragment();
                break;
            case R.id.button_home_view_users:
                replaceWithViewUsersFragment();
                break;
            case R.id.button_home_update_user:
                replaceWithUpdateUserFragment();
                break;
            case R.id.button_home_delete_user:
                replaceWithDeleteUserFragment();
                break;
            case R.id.button_home_delete_all_users:
                displayDeleteAllUsersAlertDialog();
                break;
        }
    }

    @Override
    public void onAllUsersDeleted() {
        Toast.makeText(mContext, "All users deleted successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAllUserError(Throwable e) {
        Log.d(TAG,"mytest: onDeleteAllUserError: "+e.getMessage());
    }
}
