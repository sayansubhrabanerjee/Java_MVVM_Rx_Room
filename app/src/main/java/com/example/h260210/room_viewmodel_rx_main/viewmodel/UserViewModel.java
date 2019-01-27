package com.example.h260210.room_viewmodel_rx_main.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.h260210.room_viewmodel_rx_main.model.IAddUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.IDeleteAllUsersDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.IDeleteUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.ISearchUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.IUpdateUserDBListener;
import com.example.h260210.room_viewmodel_rx_main.model.User;
import com.example.h260210.room_viewmodel_rx_main.model.UserRepo;

import java.util.List;

import io.reactivex.Flowable;

public class UserViewModel extends AndroidViewModel {
    private UserRepo mUserRepo;
    private LiveData<List<User>> mUserList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserRepo = new UserRepo(application);
        mUserList = mUserRepo.getUserList();
    }

    public LiveData<List<User>> getUserList() {
        return mUserList;
    }

    public void addUser(User user, IAddUserDBListener listener){
        mUserRepo.addUser(user,listener);
    }

    public void getUserByUsername(String username, ISearchUserDBListener listener){
        mUserRepo.getUserByUsername(username,listener);
    }

    public void updateUser(User user, IUpdateUserDBListener listener){
        mUserRepo.updateUser(user,listener);
    }

    public void deleteUser(User user, IDeleteUserDBListener listener){
        mUserRepo.deleteUser(user,listener);
    }

    public void deleteAllUsers(IDeleteAllUsersDBListener listener){
        mUserRepo.deleteAllUsers(listener);
    }
}
