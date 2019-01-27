package com.example.h260210.room_viewmodel_rx_main.model;

public interface IAddUserDBListener {

    void onUserAdded(User user);

    void onAddUserError(Throwable e);
}
