package com.example.h260210.room_viewmodel_rx_main.model;

public interface IDeleteAllUsersDBListener {

    void onAllUsersDeleted();

    void onDeleteAllUserError(Throwable e);
}
