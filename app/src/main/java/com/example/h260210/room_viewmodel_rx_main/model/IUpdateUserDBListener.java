package com.example.h260210.room_viewmodel_rx_main.model;

public interface IUpdateUserDBListener {

    void onUserUpdated(User user);

    void onUpdateUserError(Throwable e);
}
