package com.example.h260210.room_viewmodel_rx_main.model;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface IUserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertUser(User user);

    @Query("SELECT * FROM USER_TABLE")
    LiveData<List<User>> getAllUsers(); //ViewModel approach

    @Query("SELECT * FROM USER_TABLE")
    Flowable<List<User>> getAllUsersFlowable(); //Rx approach

    @Query("SELECT * FROM USER_TABLE WHERE user_name = :username")
    Single<User> getUserByUsername(String username); //Rx approach

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM USER_TABLE")
    void deleteAllUsers();
}
