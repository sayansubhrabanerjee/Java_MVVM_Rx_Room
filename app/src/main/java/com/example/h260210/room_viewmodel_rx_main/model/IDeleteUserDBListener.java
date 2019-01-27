package com.example.h260210.room_viewmodel_rx_main.model;

public interface IDeleteUserDBListener {

    void onUserDeleted(User user);

    void onDeleteUserError(Throwable e);
}
