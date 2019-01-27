package com.example.h260210.room_viewmodel_rx_main.model;

public interface ISearchUserDBListener {
    void onUserFound(User user);

    void onUserNotFound();

    void onSearchUserError(Throwable e);
}
